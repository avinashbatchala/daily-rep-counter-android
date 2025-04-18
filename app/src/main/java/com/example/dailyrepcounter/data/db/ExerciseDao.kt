// ExerciseDao.kt
package com.example.dailyrepcounter.data.db

import androidx.room.*
import com.example.dailyrepcounter.data.model.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises ORDER BY name")
    fun getAllExercises(): Flow<List<Exercise>>

    @Insert
    suspend fun insertExercise(exercise: Exercise): Long

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getExerciseById(id: Long): Exercise?
}