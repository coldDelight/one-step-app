package com.colddelight.data.repository

import com.colddelight.data.mapper.asDomain
import com.colddelight.data.mapper.asEntity
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.model.DayExercise
import com.colddelight.model.DayExerciseUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DayExerciseRepositoryImpl @Inject constructor(
    private val dayExerciseDao: DayExerciseDao,
) : DayExerciseRepository {
    override fun getDayExerciseList(): Flow<List<DayExerciseUI>> {
        return dayExerciseDao.getAllDayExercise().map { it.asDomain() }
    }

    override suspend fun deleteDayExercise(dayExerciseId: Int) {
        dayExerciseDao.deleteDayExerciseById(dayExerciseId)
    }

    override suspend fun insertDayExercise(dayExercise: DayExercise):Long {
        return dayExerciseDao.insertDayExercise(dayExercise.asEntity())
    }
}