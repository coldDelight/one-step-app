package com.colddelight.network.di

import com.colddelight.network.datasource.ExerciseDataSource
import com.colddelight.network.datasource.UserDataSource
import com.colddelight.network.datasourceImpl.ExerciseDataSourceImpl
import com.colddelight.network.datasourceImpl.UserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideUserDataSource(

    ): UserDataSource {
        return UserDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideExerciseDataSource(

    ): ExerciseDataSource {
        return ExerciseDataSourceImpl()
    }


}