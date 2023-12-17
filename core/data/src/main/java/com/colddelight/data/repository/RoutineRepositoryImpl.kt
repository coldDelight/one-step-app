package com.colddelight.data.repository

import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDao: RoutineDao,
    private val historyDao: HistoryDao,
    private val routineDayDao: RoutineDayDao,
    private val userDataSource: UserPreferencesDataSource
): RoutineRepository {
    override fun getRoutine(): Flow<Routine> {
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDao.getRoutine(routineId)
                    .map {
                        val routine = Routine(
                            name = it.name,
                            cnt = it.cnt
                        )
                        routine
                    }
            }
    }
}