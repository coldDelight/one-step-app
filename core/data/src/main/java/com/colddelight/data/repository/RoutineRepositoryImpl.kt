package com.colddelight.data.repository

import com.colddelight.data.mapper.asDomain
import com.colddelight.data.mapper.asEntity
import com.colddelight.database.dao.RoutineDao
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Routine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDao: RoutineDao,
    private val userDataSource: UserPreferencesDataSource,
) : RoutineRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRoutine(): Flow<Routine> {
        return userDataSource.currentRoutineId.flatMapLatest { routineId ->
            routineDao.getRoutine(routineId).map { routineEntity ->
                routineEntity.asDomain()
            }
        }
    }

    override suspend fun upsertRoutine(routine: Routine) {
        routineDao.upsertRoutine(routine.asEntity())
    }

}
