package com.example.data.repository

import com.example.data.local.AcademicDao
import com.example.data.local.ChapterEntity
import com.example.data.local.FocusRecordEntity
import com.example.data.local.StudentProfileEntity
import com.example.data.local.StudyTaskEntity
import com.example.data.local.TestResultEntity
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterDetail
import com.example.data.model.ChapterStatus
import com.example.data.model.FocusRecord
import com.example.data.model.FocusState
import com.example.data.model.RevisionTask
import com.example.data.model.StudentProfile
import com.example.data.model.StudyTask
import com.example.data.model.TestDifficulty
import com.example.data.model.TestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.UUID

class AcademicRepository(private val dao: AcademicDao) {

    suspend fun initializeIfEmpty() {
        val existingProfile = dao.getStudentProfile().firstOrNull()
        if (existingProfile == null) {
            dao.insertOrUpdateProfile(CurriculumDataProvider.getDefaultProfile())
            dao.insertChapters(CurriculumDataProvider.getDefaultChapters())
            dao.insertTasks(CurriculumDataProvider.getDefaultStudyTasks())
        }
    }

    fun getStudentProfile(): Flow<StudentProfile> {
        return dao.getStudentProfile().map { entity ->
            if (entity != null) {
                StudentProfile(
                    name = entity.name,
                    designation = entity.designation,
                    jnvBranch = entity.jnvBranch,
                    houseName = entity.houseName,
                    avatarIcon = entity.avatarIcon,
                    activeStream = try {
                        AcademicStream.valueOf(entity.activeStream)
                    } catch (e: Exception) {
                        AcademicStream.SCIENCE
                    },
                    streakDays = entity.streakDays,
                    totalStudyMinutes = entity.totalStudyMinutes,
                    totalFocusMinutes = entity.totalFocusMinutes,
                    chaptersCompleted = entity.chaptersCompleted,
                    testsAttempted = entity.testsAttempted,
                    avgTestAccuracy = entity.avgTestAccuracy,
                    xpPoints = entity.xpPoints,
                    rankBadge = entity.rankBadge,
                    isLoggedIn = entity.isLoggedIn
                )
            } else {
                StudentProfile()
            }
        }
    }

    suspend fun updateProfile(
        name: String,
        jnvBranch: String,
        houseName: String,
        avatarIcon: String,
        stream: AcademicStream
    ) {
        val existing = dao.getStudentProfile().firstOrNull() ?: CurriculumDataProvider.getDefaultProfile()
        val updated = existing.copy(
            name = name,
            designation = "Class 12th Navodayan",
            jnvBranch = jnvBranch,
            houseName = houseName,
            avatarIcon = avatarIcon,
            activeStream = stream.name,
            isLoggedIn = true
        )
        dao.insertOrUpdateProfile(updated)
    }

    suspend fun setActiveStream(stream: AcademicStream) {
        dao.updateActiveStream(stream.name)
    }

    fun getChapters(stream: AcademicStream): Flow<List<ChapterDetail>> {
        return dao.getChaptersByStream(stream.name).map { list ->
            list.map { it.toDomain() }
        }
    }

    suspend fun getChapterDetail(chapterId: String): ChapterDetail? {
        val entity = dao.getChapterById(chapterId) ?: return null
        return entity.toDomain()
    }

    suspend fun updateChapterProgress(chapterId: String, status: ChapterStatus, percent: Int) {
        dao.updateChapterProgress(chapterId, status.name, percent)
    }

    // Spaced repetition intelligent scheduling
    fun getRevisionTasks(stream: AcademicStream): Flow<List<RevisionTask>> {
        val now = System.currentTimeMillis()
        return dao.getCompletedChaptersForRevision(stream.name).map { list ->
            list.map { entity ->
                val intervalDays = when (entity.revisionCount) {
                    0 -> 1
                    1 -> 3
                    2 -> 7
                    3 -> 14
                    else -> 30
                }
                val isOverdue = entity.nextRevisionDue > 0L && entity.nextRevisionDue < now
                RevisionTask(
                    chapterId = entity.id,
                    subjectId = entity.subjectId,
                    stream = stream,
                    chapterTitle = entity.title,
                    subjectName = getSubjectName(entity.subjectId),
                    intervalDays = intervalDays,
                    stage = (entity.revisionCount + 1).coerceAtMost(5),
                    dueDate = entity.nextRevisionDue,
                    lastScorePercent = (entity.confidenceRating * 20),
                    confidenceRating = entity.confidenceRating,
                    isOverdue = isOverdue
                )
            }
        }
    }

    suspend fun completeRevision(chapterId: String, confidenceRating: Int, quizAccuracyPercent: Int = 80) {
        val now = System.currentTimeMillis()
        val dayMillis = 86_400_000L
        val nextIntervalDays = when {
            quizAccuracyPercent < 60 || confidenceRating <= 2 -> 1 // Earlier repetition
            quizAccuracyPercent >= 90 && confidenceRating >= 4 -> 14 // Gradual expansion
            else -> 7
        }
        val nextDue = now + (nextIntervalDays * dayMillis)
        dao.recordChapterRevision(chapterId, now, nextDue, confidenceRating)
        dao.addStudyTimeAndXp(30, 150)
    }

    // Tasks / Schedule
    fun getStudyTasks(stream: AcademicStream): Flow<List<StudyTask>> {
        return dao.getTasksByStream(stream.name).map { list ->
            list.map { entity ->
                StudyTask(
                    id = entity.id,
                    stream = stream,
                    subjectId = entity.subjectId,
                    chapterTitle = entity.chapterTitle,
                    timeSlot = entity.timeSlot,
                    durationMinutes = entity.durationMinutes,
                    isCompleted = entity.isCompleted,
                    taskType = entity.taskType
                )
            }
        }
    }

    suspend fun setTaskCompleted(taskId: String, completed: Boolean) {
        dao.setTaskCompleted(taskId, completed)
        if (completed) {
            dao.addStudyTimeAndXp(45, 100)
        }
    }

    suspend fun addTask(stream: AcademicStream, subjectId: String, title: String, timeSlot: String, duration: Int, taskType: String) {
        dao.insertTasks(
            listOf(
                StudyTaskEntity(
                    id = "task_" + UUID.randomUUID().toString().take(8),
                    stream = stream.name,
                    subjectId = subjectId,
                    chapterTitle = title,
                    timeSlot = timeSlot,
                    durationMinutes = duration,
                    isCompleted = false,
                    taskType = taskType
                )
            )
        )
    }

    suspend fun deleteTask(taskId: String) {
        dao.deleteTask(taskId)
    }

    // Focus Sessions
    fun getRecentFocusSessions(): Flow<List<FocusRecord>> {
        return dao.getRecentFocusRecords().map { list ->
            list.map {
                FocusRecord(
                    id = it.id,
                    stream = try { AcademicStream.valueOf(it.stream) } catch (e: Exception) { AcademicStream.SCIENCE },
                    subjectName = it.subjectName,
                    chapterTitle = it.chapterTitle,
                    targetMinutes = it.targetMinutes,
                    actualSeconds = it.actualSeconds,
                    state = try { FocusState.valueOf(it.state) } catch (e: Exception) { FocusState.COMPLETED },
                    startTimestamp = it.startTimestamp,
                    endTimestamp = it.endTimestamp,
                    objective = it.objective
                )
            }
        }
    }

    suspend fun saveFocusSession(record: FocusRecord) {
        dao.insertFocusRecord(
            FocusRecordEntity(
                id = record.id,
                stream = record.stream.name,
                subjectName = record.subjectName,
                chapterTitle = record.chapterTitle,
                targetMinutes = record.targetMinutes,
                actualSeconds = record.actualSeconds,
                state = record.state.name,
                startTimestamp = record.startTimestamp,
                endTimestamp = record.endTimestamp,
                objective = record.objective
            )
        )
        if (record.state == FocusState.COMPLETED) {
            val focusMinutes = (record.actualSeconds / 60).coerceAtLeast(1)
            dao.addStudyTimeAndXp(focusMinutes, focusMinutes * 10)
        }
    }

    // Tests
    fun getTestResults(stream: AcademicStream): Flow<List<TestResult>> {
        return dao.getTestResultsByStream(stream.name).map { list ->
            list.map {
                TestResult(
                    id = it.id,
                    timestamp = it.timestamp,
                    stream = stream,
                    subjectName = it.subjectName,
                    chapterTitle = it.chapterTitle,
                    difficulty = try { TestDifficulty.valueOf(it.difficulty) } catch (e: Exception) { TestDifficulty.INTERMEDIATE },
                    totalQuestions = it.totalQuestions,
                    totalMarks = it.totalMarks,
                    scoredMarks = it.scoredMarks,
                    correctCount = it.correctCount,
                    incorrectCount = it.incorrectCount,
                    accuracyPercent = it.accuracyPercent,
                    durationSeconds = it.durationSeconds,
                    weakConcepts = emptyList(),
                    feedback = it.feedback
                )
            }
        }
    }

    suspend fun recordTestAttempt(result: TestResult) {
        dao.insertTestResult(
            TestResultEntity(
                id = result.id,
                timestamp = result.timestamp,
                stream = result.stream.name,
                subjectName = result.subjectName,
                chapterTitle = result.chapterTitle,
                difficulty = result.difficulty.name,
                totalQuestions = result.totalQuestions,
                totalMarks = result.totalMarks,
                scoredMarks = result.scoredMarks,
                correctCount = result.correctCount,
                incorrectCount = result.incorrectCount,
                accuracyPercent = result.accuracyPercent,
                durationSeconds = result.durationSeconds,
                feedback = result.feedback
            )
        )
        val gainedXp = (result.scoredMarks * 25).coerceAtLeast(50)
        dao.addStudyTimeAndXp(result.durationSeconds / 60, gainedXp)
    }

    private fun getSubjectName(subjectId: String): String {
        val all = CurriculumDataProvider.scienceSubjects + CurriculumDataProvider.humanitiesSubjects
        return all.firstOrNull { it.id == subjectId }?.name ?: "General Subject"
    }

    private fun ChapterEntity.toDomain(): ChapterDetail {
        return ChapterDetail(
            id = id,
            subjectId = subjectId,
            stream = try { AcademicStream.valueOf(stream) } catch (e: Exception) { AcademicStream.SCIENCE },
            chapterNo = chapterNo,
            title = title,
            bookName = bookName,
            difficulty = difficulty,
            estimatedHours = estimatedHours,
            status = try { ChapterStatus.valueOf(status) } catch (e: Exception) { ChapterStatus.NOT_STARTED },
            completionPercent = completionPercent,
            lastRevisedAt = lastRevisedAt,
            revisionCount = revisionCount,
            nextRevisionDue = nextRevisionDue,
            confidenceRating = confidenceRating,
            ncertPdfUrl = ncertPdfUrl,
            introduction = introduction,
            learningObjectives = learningObjectives.split(";").map { it.trim() }.filter { it.isNotEmpty() },
            keyConcepts = keyConcepts.split(";").map { it.trim() }.filter { it.isNotEmpty() },
            formulasOrSummary = formulasOrSummary,
            pyqNotes = pyqNotes,
            sampleQuestions = CurriculumDataProvider.getCuratedQuestions(subjectId)
        )
    }
}
