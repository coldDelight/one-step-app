package com.colddelight.data.repository

import com.colddelight.data.mapper.asDomain
import com.colddelight.data.mapper.asEntity
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.RoutineDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineDayRepositoryImpl @Inject constructor(
    private val routineDayDao: RoutineDayDao,
    private val userDataSource: UserPreferencesDataSource,
) : RoutineDayRepository {
    override suspend fun upsertRoutineDay(routineDay: RoutineDay) {
        routineDayDao.upsertRoutineDay(routineDay.asEntity())
    }

    override suspend fun deleteRoutineDay(routineDayId: Int) {
        routineDayDao.deleteRoutineDayAndRelatedData(routineDayId)
    }

    override fun getRoutineDayList(): Flow<List<RoutineDay>> {
        return userDataSource.currentRoutineId.flatMapLatest { routineId ->
            routineDayDao.getAllRoutineDay(routineId)
        }.map { it.asDomain() }
    }

    override fun getExerciseDayList(): Flow<List<RoutineDay>> {
        return userDataSource.currentRoutineId.flatMapLatest { routineId ->
            routineDayDao.getAllWorkDay(routineId)
        }.map { it.asDomain() }
    }
}