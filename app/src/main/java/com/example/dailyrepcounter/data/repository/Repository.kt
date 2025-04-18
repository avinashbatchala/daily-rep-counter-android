// Repository.kt
package com.example.dailyrepcounter.data.repository

import com.example.dailyrepcounter.data.db.ExerciseDao
import com.example.dailyrepcounter.data.db.ExerciseStats
import com.example.dailyrepcounter.data.db.SessionDao
import com.example.dailyrepcounter.data.model.Exercise
import com.example.dailyrepcounter.data.model.Session
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class Repository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val sessionDao: SessionDao
) {
    // Exercise operations
    fun getAllExercises(): Flow<List<Exercise>> = exerciseDao.getAllExercises()

    suspend fun addExercise(exercise: Exercise): Long = exerciseDao.insertExercise(exercise)

    suspend fun deleteExercise(exercise: Exercise) = exerciseDao.deleteExercise(exercise)

    // Session operations
    suspend fun addSession(session: Session): Long = sessionDao.insertSession(session)

    fun getSessionsForExercise(exerciseId: Long): Flow<List<Session>> =
        sessionDao.getSessionsForExercise(exerciseId)

    // Dashboard data operations
    fun getDailyStats(date: LocalDate): Flow<List<ExerciseStats>> {
        val startOfDay = LocalDateTime.of(date, LocalTime.MIN)
        val endOfDay = LocalDateTime.of(date, LocalTime.MAX)
        return sessionDao.getDailyStats(startOfDay, endOfDay)
    }
}