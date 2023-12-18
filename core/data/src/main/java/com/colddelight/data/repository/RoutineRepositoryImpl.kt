package com.colddelight.data.repository

import android.util.Log
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDao: RoutineDao,
    private val historyDao: HistoryDao,
    private val dayExerciseDao: DayExerciseDao,
    private val routineDayDao: RoutineDayDao,
    private val userDataSource: UserPreferencesDataSource,
) : RoutineRepository {
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

    override fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>> {
        Log.e(javaClass.simpleName, "getRoutineWeekInfo: hi")
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDayDao.getAllRoutineDay(routineId)
                    .map {
                        it.map { routineDayEntity ->
                            val exerciseList = mutableListOf<ExerciseInfo>()
                            dayExerciseDao.getAllDayExercise(routineDayEntity.routineId)
                                .map { routineDayMap ->
                                    routineDayMap.entries.map { (dayExercise, exercise) ->
                                        val exerciseInfo = ExerciseInfo(
                                            routineDayId = dayExercise.routineDayId,
                                            exerciseId = exercise.id,
                                            exerciseName = exercise.name,
                                            index = dayExercise.index,
                                            origin = dayExercise.origin,
                                            kgList = dayExercise.kgList,
                                            repsList = dayExercise.repsList
                                        )
                                        exerciseList.add(exerciseInfo)
                                    }
                                }

                            val routineDayInfo = RoutineDayInfo(
                                routineId = routineDayEntity.id,
                                categoryList = routineDayEntity.categoryList,
                                dayOfWeek = routineDayEntity.dayOfWeek,
                                exerciseList = exerciseList
                            )
                            routineDayInfo
                        }
                    }
            }
    }
}