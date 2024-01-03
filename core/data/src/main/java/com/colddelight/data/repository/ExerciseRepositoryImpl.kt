package com.colddelight.data.repository

import android.util.Log
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.SetInfo
import com.colddelight.model.TodayRoutine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val historyExerciseDao: HistoryExerciseDao,
    private val routineDayDao: RoutineDayDao,
    private val dayExerciseDao: DayExerciseDao,
    private val exerciseDao: ExerciseDao,
    private val userDataSource: UserPreferencesDataSource
) : ExerciseRepository {

    private val todayHistoryId = historyDao.getHistoryForToday(LocalDate.now())

    val dayOfWeek = LocalDate.now().dayOfWeek.value
    override fun getTodayExerciseList(): Flow<List<Exercise>> {
        return todayHistoryId.flatMapLatest {
            historyExerciseDao.getTodayHistoryExercises(it)
                .map { historyExercises ->
                    val exerciseList = mutableListOf<Exercise>()
                    historyExercises.forEach { (dayExerciseEntity, exercise) ->
                        val exerciseItem = when (exercise.category) {
                            ExerciseCategory.CALISTHENICS ->
                                transformCalisthenicsExercise(dayExerciseEntity, exercise)

                            else -> transformWeightExercise(dayExerciseEntity, exercise)
                        }
                        exerciseList.add(exerciseItem)
                    }
                    exerciseList
                }
        }


    }

    private fun transformCalisthenicsExercise(
        historyExerciseEntity: HistoryExerciseEntity,
        exercise: ExerciseEntity
    ): Exercise.Calisthenics {
        return Exercise.Calisthenics(
            exerciseId = historyExerciseEntity.id,
            name = exercise.name,
            reps = historyExerciseEntity.repsList.maxOrNull() ?: 0,
            set = historyExerciseEntity.repsList.size,
            isDone = historyExerciseEntity.isDone,
            setInfoList = historyExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg,
                    historyExerciseEntity.repsList[index]
                )
            }
        )
    }

    private fun transformWeightExercise(
        historyExerciseEntity: HistoryExerciseEntity,
        exercise: ExerciseEntity
    ): Exercise.Weight {
        return Exercise.Weight(
            exerciseId = historyExerciseEntity.id,
            name = exercise.name,
            min = historyExerciseEntity.kgList.minOrNull() ?: 0,
            max = historyExerciseEntity.kgList.maxOrNull() ?: 0,
            isDone = historyExerciseEntity.isDone,
            setInfoList = historyExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg,
                    historyExerciseEntity.repsList[index]
                )
            }

        )
    }

    override fun getTodayRoutineInfo(): Flow<TodayRoutine> {
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDayDao.getTodayRoutineInfo(routineId, dayOfWeek)
                    .map { routineDayInfoMap ->
                        val routine = routineDayInfoMap.keys.firstOrNull()
                        val routineInfo = TodayRoutine(
                            name = routine?.name ?: "",
                            cnt = routine?.cnt ?: 0,
                            categoryIdList = routineDayInfoMap[routine]?.split(",")
                                ?.mapNotNull {
                                    it.toIntOrNull()
                                        ?.let { id -> ExerciseCategory.fromId(id) }
                                } ?: listOf()
                        )
                        routineInfo
                    }

            }
    }

    override suspend fun upDateKgList(historyExerciseId: Int, kgList: List<Int>) {
        historyExerciseDao.updateKgList(historyExerciseId, kgList)
    }

    override suspend fun upDateRepsList(historyExerciseId: Int, repsList: List<Int>) {
        historyExerciseDao.updateRepsList(historyExerciseId, repsList)
    }

    override suspend fun initExercise() {
        val id = todayHistoryId.firstOrNull() ?: -1
        when (id) {
            -1 -> {
                Log.e("TAG", "initExercise: 최초 등장")
                historyDao.insertHistory(
                    HistoryEntity(
                        1,
                        LocalDate.now(),
                        listOf(1, 2),
                        "",
                        isDone = false,
                        isFree = false
                    )
                )
                val dayExercises = dayExerciseDao.getDayExercisesByRoutineDayId(1).first()
                val historyExercises = dayExercises.map { dayExercise ->
                    HistoryExerciseEntity(
                        historyId = todayHistoryId.firstOrNull() ?: -1,
                        exerciseId = dayExercise.exerciseId,
                        index = dayExercise.index,
                        isDone = false,
                        time = "",
                        kgList = dayExercise.kgList,
                        repsList = dayExercise.repsList
                    )
                }
                historyExerciseDao.insertAll(historyExercises)
            }

            else -> {
                Log.e("TAG", "initExercise: 최초 등장아님")
            }
        }
    }

    override suspend fun addTmp() {

//        /** 해당 routine day에 모든 운동 히스토리로 옮기느 작업
////
//        val dayExercises = dayExerciseDao.getDayExercisesByRoutineDayId(1).first()
//        val historyExercises = dayExercises.map { dayExercise ->
//            HistoryExerciseEntity(
//                historyId = 1,
//                exerciseId = dayExercise.exerciseId,
//                index = dayExercise.index,
//                isDone = false,
//                time = "",
//                kgList = dayExercise.kgList,
//                repsList = dayExercise.repsList
//            )
//        }
//        historyExerciseDao.insertAll(historyExercises)
//         **/


//        routineDayDao.insertRoutineDay(RoutineDayEntity(1, dayOfWeek, listOf(1, 2)))
////        historyDao.insertHistory(
////            HistoryEntity(
////                1,
////                LocalDate.now(),
////                listOf(1, 2),
////                "",
////                isDone = false,
////                isFree = false
////            )
////        )
//        exerciseDao.insertExercise(ExerciseEntity("벤치 프레스", ExerciseCategory.ARM))
//        exerciseDao.insertExercise(ExerciseEntity("스쿼트", ExerciseCategory.LEG))
//        exerciseDao.insertExercise(ExerciseEntity("데드", ExerciseCategory.LEG))
//        exerciseDao.insertExercise(ExerciseEntity("턱걸이", ExerciseCategory.CALISTHENICS))
////
//        //여기 수정
//
//        dayExerciseDao.insertDayExercise(
//            DayExerciseEntity(
//                1,
//                1,
//                0,
//                listOf(60, 60, 80),
//                listOf(12, 12, 12),
//            )
//        )
//        dayExerciseDao.insertDayExercise(
//            DayExerciseEntity(
//                1,
//                2,
//                1,
//                listOf(60, 60, 80),
//                listOf(12, 12, 12),
//            )
//        )
//        dayExerciseDao.insertDayExercise(
//            DayExerciseEntity(
//                1,
//                3,
//                2,
//                listOf(60, 80, 100),
//                listOf(12, 12, 12),
//            )
//        )
//        dayExerciseDao.insertDayExercise(
//            DayExerciseEntity(
//                1,
//                4,
//                3,
//                listOf(0, 0, 0),
//                listOf(12, 12, 12),
//            )
//        )

    }

}



