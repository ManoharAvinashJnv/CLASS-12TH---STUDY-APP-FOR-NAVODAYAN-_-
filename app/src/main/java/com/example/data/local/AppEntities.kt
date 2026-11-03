package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey val id: String,
    val subjectId: String,
    val stream: String,
    val chapterNo: Int,
    val title: String,
    val bookName: String,
    val difficulty: String,
    val estimatedHours: Int,
    val status: String,
    val completionPercent: Int,
    val lastRevisedAt: Long,
    val revisionCount: Int,
    val nextRevisionDue: Long,
    val confidenceRating: Int,
    val ncertPdfUrl: String,
    val introduction: String,
    val learningObjectives: String, // Pipe or semicolon separated
    val keyConcepts: String,
    val formulasOrSummary: String,
    val pyqNotes: String
)

@Entity(tableName = "study_tasks")
data class StudyTaskEntity(
    @PrimaryKey val id: String,
    val stream: String,
    val subjectId: String,
    val chapterTitle: String,
    val timeSlot: String,
    val durationMinutes: Int,
    val isCompleted: Boolean,
    val taskType: String
)

@Entity(tableName = "focus_records")
data class FocusRecordEntity(
    @PrimaryKey val id: String,
    val stream: String,
    val subjectName: String,
    val chapterTitle: String,
    val targetMinutes: Int,
    val actualSeconds: Int,
    val state: String,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val objective: String
)

@Entity(tableName = "test_results")
data class TestResultEntity(
    @PrimaryKey val id: String,
    val timestamp: Long,
    val stream: String,
    val subjectName: String,
    val chapterTitle: String,
    val difficulty: String,
    val totalQuestions: Int,
    val totalMarks: Int,
    val scoredMarks: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val accuracyPercent: Int,
    val durationSeconds: Int,
    val feedback: String
)

@Entity(tableName = "student_profile")
data class StudentProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val designation: String = "Class 12th Navodayan",
    val jnvBranch: String = "JNV Central",
    val houseName: String = "Aravali House",
    val avatarIcon: String = "avatar_scholar",
    val activeStream: String,
    val streakDays: Int,
    val totalStudyMinutes: Int,
    val totalFocusMinutes: Int,
    val chaptersCompleted: Int,
    val testsAttempted: Int,
    val avgTestAccuracy: Int,
    val xpPoints: Int,
    val rankBadge: String,
    val isLoggedIn: Boolean = true
)
