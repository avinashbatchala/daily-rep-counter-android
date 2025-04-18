// SessionDao.kt
package com.example.dailyrepcounter.data.db

import androidx.room.*
import com.example.dailyrepcounter.data.model.Session
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session): Long

    @Query("SELECT * FROM sessions WHERE exerciseId = :exerciseId ORDER BY date DESC")
    fun getSessionsForExercise(exerciseId: Long): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE date BETWEEN :startDate AND :endDate")
    fun getSessionsForDateRange(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Session>>

    @Query("SELECT exerciseId, SUM(reps) as totalReps, COUNT(id) as sessionCount FROM sessions WHERE date BETWEEN :startDate AND :endDate GROUP BY exerciseId")
    fun getDailyStats(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<ExerciseStats>>
}

data class ExerciseStats(
    val exerciseId: Long,
    val totalReps: Int,
    val sessionCount: Int
)