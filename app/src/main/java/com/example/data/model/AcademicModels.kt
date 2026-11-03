package com.example.data.model

enum class AcademicStream(val displayName: String, val subtitle: String, val iconEmoji: String) {
    SCIENCE("Science Corner", "PCM, PCB & Computer Science", "⚛️"),
    HUMANITIES("Humanities Corner", "Arts, Social Sciences & Languages", "🏛️")
}

enum class ChapterStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}

enum class FocusState {
    IDLE,
    PREPARING,
    ACTIVE,
    BREAK,
    COMPLETED,
    CANCELLED,
    INTERRUPTED
}

enum class QuestionType(val label: String) {
    MCQ("Multiple Choice"),
    MULTI_SELECT("Multiple Select"),
    TRUE_FALSE("True or False"),
    ASSERTION_REASON("Assertion & Reason"),
    FILL_BLANKS("Fill in Blanks"),
    SHORT_ANSWER("Short Answer / Numerical")
}

enum class TestDifficulty(val label: String, val weight: Float) {
    BEGINNER("Beginner", 1.0f),
    INTERMEDIATE("Intermediate", 1.5f),
    ADVANCED("Advanced", 2.0f),
    EXAM_LEVEL("CBSE Board Exam Level", 2.5f)
}

data class SubjectInfo(
    val id: String,
    val stream: AcademicStream,
    val name: String,
    val code: String,
    val bookTitle: String,
    val iconEmoji: String,
    val description: String,
    val colorHex: Long
)

data class QuizQuestion(
    val id: String,
    val chapterId: String,
    val questionText: String,
    val type: QuestionType,
    val options: List<String>,
    val correctIndices: List<Int>,
    val explanation: String,
    val marks: Int = 1
)

data class ChapterDetail(
    val id: String,
    val subjectId: String,
    val stream: AcademicStream,
    val chapterNo: Int,
    val title: String,
    val bookName: String,
    val difficulty: String,
    val estimatedHours: Int,
    val status: ChapterStatus,
    val completionPercent: Int,
    val lastRevisedAt: Long = 0L,
    val revisionCount: Int = 0,
    val nextRevisionDue: Long = 0L,
    val confidenceRating: Int = 3,
    val ncertPdfUrl: String,
    val introduction: String,
    val learningObjectives: List<String>,
    val keyConcepts: List<String>,
    val formulasOrSummary: String,
    val pyqNotes: String,
    val sampleQuestions: List<QuizQuestion> = emptyList()
)

data class StudyTask(
    val id: String,
    val stream: AcademicStream,
    val subjectId: String,
    val chapterTitle: String,
    val timeSlot: String,
    val durationMinutes: Int,
    val isCompleted: Boolean,
    val taskType: String // e.g. "Theory Reading", "Formula Revision", "Numerical Practice", "Mock Test"
)

data class FocusRecord(
    val id: String,
    val stream: AcademicStream,
    val subjectName: String,
    val chapterTitle: String,
    val targetMinutes: Int,
    val actualSeconds: Int,
    val state: FocusState,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val objective: String
)

data class RevisionTask(
    val chapterId: String,
    val subjectId: String,
    val stream: AcademicStream,
    val chapterTitle: String,
    val subjectName: String,
    val intervalDays: Int,
    val stage: Int, // 1 to 5
    val dueDate: Long,
    val lastScorePercent: Int,
    val confidenceRating: Int,
    val isOverdue: Boolean
)

data class TestResult(
    val id: String,
    val timestamp: Long,
    val stream: AcademicStream,
    val subjectName: String,
    val chapterTitle: String,
    val difficulty: TestDifficulty,
    val totalQuestions: Int,
    val totalMarks: Int,
    val scoredMarks: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val accuracyPercent: Int,
    val durationSeconds: Int,
    val weakConcepts: List<String>,
    val feedback: String
)

data class StudentProfile(
    val name: String = "Class 12th Navodayan",
    val designation: String = "Class 12th Navodayan",
    val jnvBranch: String = "JNV Central",
    val houseName: String = "Aravali House",
    val avatarIcon: String = "avatar_scholar",
    val activeStream: AcademicStream = AcademicStream.SCIENCE,
    val streakDays: Int = 7,
    val totalStudyMinutes: Int = 1580,
    val totalFocusMinutes: Int = 960,
    val chaptersCompleted: Int = 6,
    val testsAttempted: Int = 14,
    val avgTestAccuracy: Int = 88,
    val xpPoints: Int = 4920,
    val rankBadge: String = "National Navodaya Scholar",
    val isLoggedIn: Boolean = true
)

data class LivePeerStudent(
    val id: String,
    val name: String,
    val designation: String = "Class 12th Navodayan",
    val jnvSchool: String,
    val subjectStudying: String,
    val currentFocusMinutes: Int,
    val streakDays: Int,
    val isOnline: Boolean = true
)

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean,
    val progress: Int,
    val maxProgress: Int,
    val category: String
)
