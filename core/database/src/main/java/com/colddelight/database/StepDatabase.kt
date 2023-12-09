package com.colddelight.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.model.ExerciseEntity

@Database(
    entities = [ExerciseEntity::class],
    version = 1
)
abstract class StepDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}