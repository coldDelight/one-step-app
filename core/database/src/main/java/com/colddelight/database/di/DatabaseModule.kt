package com.colddelight.database.di

import android.content.Context
import androidx.room.Room
import com.colddelight.database.StepDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): StepDatabase = Room.databaseBuilder(
        context,
        StepDatabase::class.java,
        "step-database"
    ).build()
}
