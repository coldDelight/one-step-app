package com.colddelight.data.repository

import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.HistoryEntity
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.TodayRoutine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val routineDayDao: RoutineDayDao,
    private val userDataSource: UserPreferencesDataSource
) : ExerciseRepository {
    override fun getTodayRoutineInfo(): Flow<TodayRoutine> {
        return userDataSource.currentRoutineId
            .flatMapLatest { userId ->
                routineDayDao.getTodayRoutineInfo(userId, 1)
                    .map { routineDayInfoMap ->
                        val routine = routineDayInfoMap.keys.firstOrNull()
                        val routineInfo = TodayRoutine(
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

    }

    override suspend fun add() {
        historyDao.insertHistory(
            HistoryEntity(
                createdTime = LocalDate.now(),
                isDone = false,
                categoryList = listOf(1, 2),
                isFree = false,
                routineId = 1,
                totalTime = ""
            )
        )
    }
}



