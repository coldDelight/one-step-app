package com.colddelight.database.di

import com.colddelight.database.StepDatabase
import com.colddelight.database.dao.ExerciseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesAuthorDao(
        database: StepDatabase,
    ): ExerciseDao = database.exerciseDao()
}
