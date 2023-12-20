package com.colddelight.data.repository

import android.util.Log
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDao: RoutineDao,
    private val dayExerciseDao: DayExerciseDao,
    private val exerciseDao: ExerciseDao,
    private val routineDayDao: RoutineDayDao,
    private val userDataSource: UserPreferencesDataSource,
) : RoutineRepository {

    override fun getRoutine(): Flow<Routine> {
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDao.getRoutine(routineId)
                    .map {
                        val routine = Routine(
                            id = it.id,
                            name = it.name,
                            cnt = it.cnt
                        )
                        routine
                    }
            }
    }

    override fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>> {
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDayDao.getAllRoutineDay(routineId)
                    .flatMapLatest { routineDayList ->
                        flow {
                            // 누락된 요일을 순서대로 생성
                            val missingDays = (1..7).toSet() - routineDayList.map { it.dayOfWeek }.toSet()

                            // 누락된 요일에 대한 RoutineDayInfo 객체를 생성하고 리스트에 추가
                            val routineDayInfoList = routineDayList.map { routineDayEntity ->
                                val exerciseList = mutableListOf<Exercise>()

                                val routineDayMap = dayExerciseDao.getDayExercise(routineDayEntity.id).first()

                                routineDayMap.entries.forEach { (dayExercise, exercise) ->
                                    when(exercise.category){
                                        ExerciseCategory.CALISTHENICS ->
                                            Exercise.Calisthenics(
                                                exerciseId = exercise.id,
                                                name = exercise.name,
                                                time = "",
                                                reps = dayExercise.repsList.maxOrNull() ?: 0,
                                                set = dayExercise.repsList.size
                                            )

                                        else ->
                                            Exercise.Weight(
                                                exerciseId = exercise.id,
                                                name = exercise.name,
                                                time = "",
                                                min = dayExercise.kgList.minOrNull() ?: 0,
                                                max = dayExercise.kgList.maxOrNull() ?: 0
                                            )
                                    }.apply {
                                        exerciseList.add(this)
                                    }

                                }
                                RoutineDayInfo(
                                    routineId = routineDayEntity.routineId,
                                    routineDayId = routineDayEntity.id,
                                    categoryList = routineDayEntity.categoryList,
                                    dayOfWeek = routineDayEntity.dayOfWeek,
                                    exerciseList = exerciseList
                                )
                            }.toMutableList()

                            // 누락된 요일에 대한 RoutineDayInfo 객체 생성 및 추가
                            missingDays.forEach { missingDay ->
                                val nullRoutineDayInfo = RoutineDayInfo(
                                    routineId = routineId,
                                    routineDayId = null,
                                    dayOfWeek = missingDay,
                                    categoryList = emptyList(),
                                    exerciseList = emptyList()
                                )
                                routineDayInfoList.add(nullRoutineDayInfo)
                            }

                            // dayOfWeek로 정렬
                            val sortedRoutineDayInfoList = routineDayInfoList.sortedBy { it.dayOfWeek }

                            emit(sortedRoutineDayInfoList)
                        }
                    }
            }
    }










//    override fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>> {
//        return userDataSource.currentRoutineId
//            .flatMapLatest { routineId ->
//                routineDayDao.getAllRoutineDay(routineId)
//                    .map { routineDayEntities ->
//                        val existingDays = routineDayEntities.mapNotNull { it.dayOfWeek }
//                        val defaultDays = (1..7).toList() - existingDays
//                        val defaultRoutineDayInfoList = createDefaultRoutineDayInfoList(routineId, defaultDays)
//                        routineDayEntities.map { routineDayEntity ->
//                            val exerciseList = mutableListOf<ExerciseInfo>()
//                            dayExerciseDao.getAllDayExercise(routineDayEntity.routineId)
//                                .map { routineDayMap ->
//                                    routineDayMap.entries.map { (dayExercise, exercise) ->
//                                        val exerciseInfo = ExerciseInfo(
//                                            routineDayId = dayExercise.routineDayId,
//                                            exerciseId = exercise.id,
//                                            exerciseName = exercise.name,
//                                            index = dayExercise.index,
//                                            origin = dayExercise.origin,
//                                            kgList = dayExercise.kgList,
//                                            repsList = dayExercise.repsList
//                                        )
//                                        exerciseList.add(exerciseInfo)
//                                    }
//                                }
//
//                            val routineDayInfo = RoutineDayInfo(
//                                routineDayId = routineDayEntity.id,
//                                routineId = routineDayEntity.routineId,
//                                categoryList = routineDayEntity.categoryList,
//                                dayOfWeek = routineDayEntity.dayOfWeek ?: 0, // Use 0 as default if dayOfWeek is null
//                                exerciseList = exerciseList
//                            )
//                            routineDayInfo
//                        } + defaultRoutineDayInfoList
//                    }
//            }
//    }

    override suspend fun addRoutine(): List<ExerciseEntity> {
        //routineDayDao.deleteRoutineDayAndRelatedData(9)

        /** 1. Exercise Add
        exerciseDao.insertExercise(ExerciseEntity("벤치프레스",ExerciseCategory.CHEST))
        exerciseDao.insertExercise(ExerciseEntity("덤벨 컬",ExerciseCategory.ARM))
        exerciseDao.insertExercise(ExerciseEntity("턱걸이",ExerciseCategory.CALISTHENICS))**/

        /** 2. RoutineDay Add
        //routineDayDao.insertRoutineDay(RoutineDayEntity(1,2, listOf(1,2)))**/

        /** 3. DayExercise Add
        //dayExerciseDao.insertDayExercise(DayExerciseEntity(1,1,0, listOf(20,40), listOf(12,12)))
        //dayExerciseDao.insertDayExercise(DayExerciseEntity(1,2,1, listOf(40,60), listOf(10,10)))
        //dayExerciseDao.insertDayExercise(DayExerciseEntity(1,3,1, listOf(0,0), listOf(20,20)))**/
        Log.e("TAG", "나 로그찍는다?: ", )
        return exerciseDao.getExercise().first()
    }

    override suspend fun check(): Set<DayExerciseEntity> {
        return dayExerciseDao.getAllDayExercise(1).first().keys
    }

//    private fun createDefaultRoutineDayInfoList(routineId:Int, defaultDays: List<Int>): List<RoutineDayInfo> {
//        return defaultDays.map { dayOfWeek ->
//            RoutineDayInfo(
//                routineDayId = null,
//                routineId = routineId, // You can set a default value for routineId
//                categoryList = null, // You can set default values for other properties as needed
//                dayOfWeek = dayOfWeek,
//                exerciseList = null // You can set default values for exerciseList as needed
//            )
//        }
//    }

}