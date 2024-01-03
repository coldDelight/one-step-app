package com.colddelight.database.di

import com.colddelight.database.StepDatabase
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun providesExerciseDao(
        database: StepDatabase,
    ): ExerciseDao = database.exerciseDao()

    @Provides
    fun providesRoutineDao(
        database: StepDatabase,
    ): RoutineDao = database.routineDao()

    @Provides
    fun providesRoutineDayDao(
        database: StepDatabase,
    ): RoutineDayDao = database.routineDayDao()

    @Provides
    fun providesDayExerciseDao(
        database: StepDatabase,
    ): DayExerciseDao = database.dayExerciseDao()

    @Provides
    fun providesHistoryDao(
        database: StepDatabase,
    ): HistoryDao = database.historyDao()

    @Provides
    fun providesHistoryExerciseDao(
        database: StepDatabase,
    ): HistoryExerciseDao = database.historyExerciseDao()
}
