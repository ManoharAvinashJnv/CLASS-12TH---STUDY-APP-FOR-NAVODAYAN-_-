package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.AiAcademicEngine
import com.example.data.local.AppDatabase
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterDetail
import com.example.data.model.ChapterStatus
import com.example.data.model.FocusRecord
import com.example.data.model.FocusState
import com.example.data.model.QuestionType
import com.example.data.model.QuizQuestion
import com.example.data.model.RevisionTask
import com.example.data.model.StudentProfile
import com.example.data.model.StudyTask
import com.example.data.model.SubjectInfo
import com.example.data.model.TestDifficulty
import com.example.data.model.TestResult
import com.example.data.repository.AcademicRepository
import com.example.data.repository.CurriculumDataProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

import com.example.data.model.LivePeerStudent
import com.example.ui.theme.EyeComfortTheme

data class FocusUiState(
    val state: FocusState = FocusState.IDLE,
    val subjectName: String = "Physics",
    val chapterTitle: String = "Electric Charges and Fields",
    val objective: String = "Master Gauss's Law derivations and practice 5 NCERT exercises",
    val targetMinutes: Int = 25,
    val remainingSeconds: Int = 25 * 60,
    val elapsedActiveSeconds: Int = 0,
    val startTimestamp: Long = 0L,
    val isDistractionFreeActive: Boolean = false,
    val isDndMuteActive: Boolean = true,
    val isScreenAwake: Boolean = true
)

data class LiveTestState(
    val isActive: Boolean = false,
    val isCompleted: Boolean = false,
    val subjectName: String = "Physics",
    val chapterTitle: String = "Electric Charges and Fields",
    val difficulty: TestDifficulty = TestDifficulty.INTERMEDIATE,
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val userAnswers: Map<Int, List<Int>> = emptyMap(), // questionIndex -> list of chosen option indices
    val remainingTimeSeconds: Int = 600,
    val latestResult: TestResult? = null
)

data class AiChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: String, // "user" or "ai"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class AcademicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AcademicRepository

    private val _activeStream = MutableStateFlow(AcademicStream.SCIENCE)
    val activeStream: StateFlow<AcademicStream> = _activeStream.asStateFlow()

    private val _eyeTheme = MutableStateFlow(EyeComfortTheme.MIDNIGHT_NAVY)
    val eyeTheme: StateFlow<EyeComfortTheme> = _eyeTheme.asStateFlow()

    private val _showWelcomePopup = MutableStateFlow(true)
    val showWelcomePopup: StateFlow<Boolean> = _showWelcomePopup.asStateFlow()

    private val _showProfileDialog = MutableStateFlow(false)
    val showProfileDialog: StateFlow<Boolean> = _showProfileDialog.asStateFlow()

    private val _livePeers = MutableStateFlow<List<LivePeerStudent>>(CurriculumDataProvider.getLivePeers())
    val livePeers: StateFlow<List<LivePeerStudent>> = _livePeers.asStateFlow()

    private val _selectedChapter = MutableStateFlow<ChapterDetail?>(null)
    val selectedChapter: StateFlow<ChapterDetail?> = _selectedChapter.asStateFlow()

    // Focus state machine
    private val _focusUiState = MutableStateFlow(FocusUiState())
    val focusUiState: StateFlow<FocusUiState> = _focusUiState.asStateFlow()
    private var focusTimerJob: Job? = null

    // Live Test state machine
    private val _liveTestState = MutableStateFlow(LiveTestState())
    val liveTestState: StateFlow<LiveTestState> = _liveTestState.asStateFlow()
    private var testTimerJob: Job? = null

    // AI Assistant state
    private val _aiMessages = MutableStateFlow<List<AiChatMessage>>(
        listOf(
            AiChatMessage(
                sender = "ai",
                text = "👋 Welcome to your **Class 12 AI Academic Tutor**! Developed by & for Navodayans (JNV).\n\nAsk me any concept, theorem derivation, NCERT exercise, or CBSE 95%+ strategy!"
            )
        )
    )
    val aiMessages: StateFlow<List<AiChatMessage>> = _aiMessages.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    init {
        val db = AppDatabase.getInstance(application)
        repository = AcademicRepository(db.academicDao())
        viewModelScope.launch {
            repository.initializeIfEmpty()
        }
    }

    fun setEyeTheme(theme: EyeComfortTheme) {
        _eyeTheme.value = theme
    }

    fun dismissWelcomePopup() {
        _showWelcomePopup.value = false
    }

    fun setProfileDialogVisible(visible: Boolean) {
        _showProfileDialog.value = visible
    }

    fun updateStudentProfile(
        name: String,
        jnvBranch: String,
        house: String,
        avatar: String,
        stream: AcademicStream
    ) {
        viewModelScope.launch {
            repository.updateProfile(name, jnvBranch, house, avatar, stream)
            _activeStream.value = stream
            _showProfileDialog.value = false
        }
    }

    val studentProfile: StateFlow<StudentProfile> = repository.getStudentProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StudentProfile())

    val streamChapters: StateFlow<List<ChapterDetail>> = _activeStream.flatMapLatest { stream ->
        repository.getChapters(stream)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val studyTasks: StateFlow<List<StudyTask>> = _activeStream.flatMapLatest { stream ->
        repository.getStudyTasks(stream)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val revisionTasks: StateFlow<List<RevisionTask>> = _activeStream.flatMapLatest { stream ->
        repository.getRevisionTasks(stream)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val testResults: StateFlow<List<TestResult>> = _activeStream.flatMapLatest { stream ->
        repository.getTestResults(stream)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentFocusSessions: StateFlow<List<FocusRecord>> = repository.getRecentFocusSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun switchStream(newStream: AcademicStream) {
        _activeStream.value = newStream
        viewModelScope.launch {
            repository.setActiveStream(newStream)
        }
    }

    fun selectChapter(chapter: ChapterDetail?) {
        _selectedChapter.value = chapter
    }

    fun markChapterStatus(chapterId: String, status: ChapterStatus, percent: Int) {
        viewModelScope.launch {
            repository.updateChapterProgress(chapterId, status, percent)
        }
    }

    fun completeRevision(chapterId: String, confidenceRating: Int) {
        viewModelScope.launch {
            repository.completeRevision(chapterId, confidenceRating)
        }
    }

    fun toggleTask(taskId: String, currentStatus: Boolean) {
        viewModelScope.launch {
            repository.setTaskCompleted(taskId, !currentStatus)
        }
    }

    fun addCustomTask(title: String, subjectId: String, timeSlot: String, durationMinutes: Int, type: String) {
        viewModelScope.launch {
            repository.addTask(_activeStream.value, subjectId, title, timeSlot, durationMinutes, type)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    // FOCUS ENGINE STATE MACHINE
    fun configureFocusSession(subject: String, chapter: String, targetMins: Int, objective: String) {
        _focusUiState.value = _focusUiState.value.copy(
            subjectName = subject,
            chapterTitle = chapter,
            targetMinutes = targetMins,
            remainingSeconds = targetMins * 60,
            elapsedActiveSeconds = 0,
            objective = objective,
            state = FocusState.PREPARING
        )
    }

    fun startFocusSession() {
        val now = System.currentTimeMillis()
        _focusUiState.value = _focusUiState.value.copy(
            state = FocusState.ACTIVE,
            startTimestamp = now,
            isDistractionFreeActive = true
        )
        runFocusTimer()
    }

    fun pauseFocusSession() {
        focusTimerJob?.cancel()
        _focusUiState.value = _focusUiState.value.copy(state = FocusState.BREAK)
    }

    fun resumeFocusSession() {
        _focusUiState.value = _focusUiState.value.copy(state = FocusState.ACTIVE)
        runFocusTimer()
    }

    fun cancelFocusSession() {
        focusTimerJob?.cancel()
        val current = _focusUiState.value
        val now = System.currentTimeMillis()
        val record = FocusRecord(
            id = "focus_" + UUID.randomUUID().toString().take(8),
            stream = _activeStream.value,
            subjectName = current.subjectName,
            chapterTitle = current.chapterTitle,
            targetMinutes = current.targetMinutes,
            actualSeconds = current.elapsedActiveSeconds,
            state = FocusState.CANCELLED,
            startTimestamp = current.startTimestamp,
            endTimestamp = now,
            objective = current.objective
        )
        viewModelScope.launch {
            repository.saveFocusSession(record)
        }
        _focusUiState.value = FocusUiState(state = FocusState.IDLE)
    }

    private fun runFocusTimer() {
        focusTimerJob?.cancel()
        focusTimerJob = viewModelScope.launch {
            while (_focusUiState.value.state == FocusState.ACTIVE && _focusUiState.value.remainingSeconds > 0) {
                delay(1000)
                val curr = _focusUiState.value
                val newRemaining = curr.remainingSeconds - 1
                val newElapsed = curr.elapsedActiveSeconds + 1
                if (newRemaining <= 0) {
                    // Completed!
                    val now = System.currentTimeMillis()
                    val record = FocusRecord(
                        id = "focus_" + UUID.randomUUID().toString().take(8),
                        stream = _activeStream.value,
                        subjectName = curr.subjectName,
                        chapterTitle = curr.chapterTitle,
                        targetMinutes = curr.targetMinutes,
                        actualSeconds = newElapsed,
                        state = FocusState.COMPLETED,
                        startTimestamp = curr.startTimestamp,
                        endTimestamp = now,
                        objective = curr.objective
                    )
                    repository.saveFocusSession(record)
                    _focusUiState.value = curr.copy(
                        remainingSeconds = 0,
                        elapsedActiveSeconds = newElapsed,
                        state = FocusState.COMPLETED,
                        isDistractionFreeActive = false
                    )
                    break
                } else {
                    _focusUiState.value = curr.copy(
                        remainingSeconds = newRemaining,
                        elapsedActiveSeconds = newElapsed
                    )
                }
            }
        }
    }

    fun exitFocusMode() {
        _focusUiState.value = _focusUiState.value.copy(isDistractionFreeActive = false)
    }

    // TEST ENGINE
    fun startTest(subject: SubjectInfo, chapterTitle: String, difficulty: TestDifficulty, questionCount: Int = 5) {
        viewModelScope.launch {
            val questions = AiAcademicEngine.generateDynamicQuestions(
                stream = _activeStream.value,
                subjectId = subject.id,
                chapterTitle = chapterTitle,
                difficulty = difficulty,
                count = questionCount
            )
            val durationSeconds = questionCount * 120 // 2 minutes per question
            _liveTestState.value = LiveTestState(
                isActive = true,
                isCompleted = false,
                subjectName = subject.name,
                chapterTitle = chapterTitle,
                difficulty = difficulty,
                questions = questions,
                currentQuestionIndex = 0,
                userAnswers = emptyMap(),
                remainingTimeSeconds = durationSeconds,
                latestResult = null
            )
            runTestTimer()
        }
    }

    private fun runTestTimer() {
        testTimerJob?.cancel()
        testTimerJob = viewModelScope.launch {
            while (_liveTestState.value.isActive && _liveTestState.value.remainingTimeSeconds > 0) {
                delay(1000)
                val curr = _liveTestState.value
                val nextTime = curr.remainingTimeSeconds - 1
                if (nextTime <= 0) {
                    submitTest()
                    break
                } else {
                    _liveTestState.value = curr.copy(remainingTimeSeconds = nextTime)
                }
            }
        }
    }

    fun selectTestAnswer(questionIndex: Int, optionIndex: Int, isMultiSelect: Boolean) {
        val current = _liveTestState.value
        val map = current.userAnswers.toMutableMap()
        if (isMultiSelect) {
            val list = map[questionIndex]?.toMutableList() ?: mutableListOf()
            if (list.contains(optionIndex)) {
                list.remove(optionIndex)
            } else {
                list.add(optionIndex)
            }
            map[questionIndex] = list
        } else {
            map[questionIndex] = listOf(optionIndex)
        }
        _liveTestState.value = current.copy(userAnswers = map)
    }

    fun navigateQuestion(newIndex: Int) {
        val current = _liveTestState.value
        if (newIndex in current.questions.indices) {
            _liveTestState.value = current.copy(currentQuestionIndex = newIndex)
        }
    }

    fun submitTest() {
        testTimerJob?.cancel()
        val current = _liveTestState.value
        var scoredMarks = 0
        var totalMarks = 0
        var correctCount = 0
        var incorrectCount = 0
        val weakConcepts = mutableListOf<String>()

        current.questions.forEachIndexed { index, question ->
            totalMarks += question.marks
            val userChosen = current.userAnswers[index] ?: emptyList()
            val isCorrect = userChosen.sorted() == question.correctIndices.sorted()
            if (isCorrect) {
                scoredMarks += question.marks
                correctCount++
            } else {
                incorrectCount++
                weakConcepts.add("Question ${index + 1}: ${question.questionText.take(40)}...")
            }
        }

        val accuracy = if (totalMarks > 0) ((scoredMarks.toFloat() / totalMarks) * 100).toInt() else 0
        val feedback = when {
            accuracy >= 90 -> "🌟 Outstanding Mastery! Ready for CBSE Board 95%+ marks."
            accuracy >= 70 -> "👍 Solid performance. Practice minor weak formulas to achieve 90%+."
            else -> "⚠️ Needs Revision! Re-read NCERT theory and review step explanations below."
        }

        val result = TestResult(
            id = "test_" + UUID.randomUUID().toString().take(8),
            timestamp = System.currentTimeMillis(),
            stream = _activeStream.value,
            subjectName = current.subjectName,
            chapterTitle = current.chapterTitle,
            difficulty = current.difficulty,
            totalQuestions = current.questions.size,
            totalMarks = totalMarks,
            scoredMarks = scoredMarks,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            accuracyPercent = accuracy,
            durationSeconds = (current.questions.size * 120) - current.remainingTimeSeconds,
            weakConcepts = weakConcepts,
            feedback = feedback
        )

        viewModelScope.launch {
            repository.recordTestAttempt(result)
        }

        _liveTestState.value = current.copy(
            isActive = false,
            isCompleted = true,
            latestResult = result
        )
    }

    fun exitTestResult() {
        _liveTestState.value = LiveTestState()
    }

    // AI ASSISTANT CHAT
    fun sendAiPrompt(userQuery: String) {
        if (userQuery.isBlank()) return
        val currentList = _aiMessages.value.toMutableList()
        currentList.add(AiChatMessage(sender = "user", text = userQuery))
        _aiMessages.value = currentList
        _isAiLoading.value = true

        viewModelScope.launch {
            val response = AiAcademicEngine.explainConcept(
                stream = _activeStream.value,
                subject = if (_activeStream.value == AcademicStream.SCIENCE) "Physics & Chemistry" else "History & Political Science",
                concept = userQuery
            )
            _isAiLoading.value = false
            val updated = _aiMessages.value.toMutableList()
            updated.add(AiChatMessage(sender = "ai", text = response))
            _aiMessages.value = updated
        }
    }
}
