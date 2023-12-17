package com.colddelight.data.di

import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.data.repository.ExerciseRepositoryImpl
import com.colddelight.data.util.ConnectivityManagerNetworkMonitor
import com.colddelight.data.util.LoginHelper
import com.colddelight.data.util.LoginHelperImpl
import com.colddelight.data.util.NetworkMonitor
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
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    fun bindsLoginHelper(
        loginHelper: LoginHelperImpl
    ): LoginHelper
}
