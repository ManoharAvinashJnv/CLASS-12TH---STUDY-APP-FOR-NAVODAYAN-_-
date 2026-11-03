package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AcademicDao {

    // Chapters
    @Query("SELECT * FROM chapters WHERE stream = :stream ORDER BY subjectId, chapterNo ASC")
    fun getChaptersByStream(stream: String): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    suspend fun getChapterById(chapterId: String): ChapterEntity?

    @Query("SELECT * FROM chapters WHERE stream = :stream AND subjectId = :subjectId ORDER BY chapterNo ASC")
    fun getChaptersBySubject(stream: String, subjectId: String): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE status = 'COMPLETED' AND stream = :stream ORDER BY nextRevisionDue ASC")
    fun getCompletedChaptersForRevision(stream: String): Flow<List<ChapterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Update
    suspend fun updateChapter(chapter: ChapterEntity)

    @Query("UPDATE chapters SET status = :status, completionPercent = :percent WHERE id = :chapterId")
    suspend fun updateChapterProgress(chapterId: String, status: String, percent: Int)

    @Query("UPDATE chapters SET lastRevisedAt = :revisedAt, revisionCount = revisionCount + 1, nextRevisionDue = :nextDue, confidenceRating = :confidence WHERE id = :chapterId")
    suspend fun recordChapterRevision(chapterId: String, revisedAt: Long, nextDue: Long, confidence: Int)

    // Study Tasks
    @Query("SELECT * FROM study_tasks WHERE stream = :stream")
    fun getTasksByStream(stream: String): Flow<List<StudyTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<StudyTaskEntity>)

    @Query("UPDATE study_tasks SET isCompleted = :completed WHERE id = :taskId")
    suspend fun setTaskCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM study_tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: String)

    // Focus Sessions
    @Query("SELECT * FROM focus_records ORDER BY startTimestamp DESC LIMIT 20")
    fun getRecentFocusRecords(): Flow<List<FocusRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFocusRecord(record: FocusRecordEntity)

    // Test Results
    @Query("SELECT * FROM test_results WHERE stream = :stream ORDER BY timestamp DESC")
    fun getTestResultsByStream(stream: String): Flow<List<TestResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestResult(result: TestResultEntity)

    // Profile
    @Query("SELECT * FROM student_profile WHERE id = 1")
    fun getStudentProfile(): Flow<StudentProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: StudentProfileEntity)

    @Query("UPDATE student_profile SET activeStream = :stream WHERE id = 1")
    suspend fun updateActiveStream(stream: String)

    @Query("UPDATE student_profile SET totalStudyMinutes = totalStudyMinutes + :minutes, xpPoints = xpPoints + :xp WHERE id = 1")
    suspend fun addStudyTimeAndXp(minutes: Int, xp: Int)
}
