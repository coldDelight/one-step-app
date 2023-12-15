package com.colddelight.data.repository


import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.RoutineInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDayDao: RoutineDayDao,
    private val userDataSource: UserPreferencesDataSource
) : RoutineRepository {
    override fun getTodayRoutineInfo(): Flow<RoutineInfo> {
        return userDataSource.currentRoutineId
            .flatMapLatest { userId ->
                routineDayDao.getTodayRoutineInfo(userId, 1)
                    .map { routineDayInfoMap ->
                        val routine = routineDayInfoMap.keys.firstOrNull()
                        val routineInfo = RoutineInfo(
                            name = routine?.name ?: "",
                            cnt = routine?.cnt ?: 0,
                            categoryIdList = routineDayInfoMap[routine]?.split(",")?.mapNotNull {
                                it.toIntOrNull()
                                    ?.let { id -> ExerciseCategory.fromId(id) }
                            } ?: listOf()
                        )
                        routineInfo
                    }
            }
    }

    override suspend fun addRoutine() {

//        routineDayDao.insertRoutineDay(
//            RoutineDayEntity(
//                1,
//                1,
//                listOf(1, 2)
//            )
//        )
    }
}
