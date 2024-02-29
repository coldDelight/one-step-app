package com.colddelight.data.di

import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.data.repository.ExerciseRepositoryImpl
import com.colddelight.data.repository.HistoryRepository
import com.colddelight.data.repository.HistoryRepositoryImpl
import com.colddelight.data.repository.HomeRepository
import com.colddelight.data.repository.HomeRepositoryImpl
import com.colddelight.data.repository.RoutineRepository
import com.colddelight.data.repository.RoutineRepositoryImpl
import com.colddelight.datastore.datasource.TokenPreferencesDataSource
import com.colddelight.datastore.datasource.TokenPreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    abstract fun bindLocalDataSource(
        dataSource: TokenPreferencesDataSourceImpl,
    ): TokenPreferencesDataSource

    @Binds
    fun bindsExerciseRepository(
        exerciseRepository: ExerciseRepositoryImpl
    ): ExerciseRepository

    @Binds
    fun bindsHomeRepository(
        homeRepository: HomeRepositoryImpl
    ): HomeRepository


    @Binds
    fun bindsRoutineRepository(
        routineRepository: RoutineRepositoryImpl
    ): RoutineRepository

    @Binds
    fun bindsHistoryRepository(
        historyRepository: HistoryRepositoryImpl
    ): HistoryRepository


}
