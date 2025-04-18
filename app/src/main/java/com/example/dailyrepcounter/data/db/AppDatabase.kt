// AppDatabase.kt
package com.example.dailyrepcounter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dailyrepcounter.data.model.Exercise
import com.example.dailyrepcounter.data.model.Session

@Database(entities = [Exercise::class, Session::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun sessionDao(): SessionDao
}