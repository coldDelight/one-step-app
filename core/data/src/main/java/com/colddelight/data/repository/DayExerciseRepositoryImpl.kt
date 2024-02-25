package com.colddelight.data.repository

import com.colddelight.data.mapper.asDomain
import com.colddelight.data.mapper.asEntity
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.model.DayExerciseWithExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DayExerciseRepositoryImpl @Inject constructor(
    private val dayExerciseDao: DayExerciseDao,
) : DayExerciseRepository {
    override fun getDayExerciseList(): Flow<List<DayExerciseWithExercise>> {
        return dayExerciseDao.getAllDayExercise().map { it.asDomain() }
    }

    override suspend fun deleteDayExercise(dayExerciseId: Int) {
        dayExerciseDao.deleteDayExerciseById(dayExerciseId)
    }

    override suspend fun insertDayExercise(dayExerciseWithExercise: DayExerciseWithExercise):Long {
        return dayExerciseDao.insertDayExercise(dayExerciseWithExercise.asEntity())
    }
}