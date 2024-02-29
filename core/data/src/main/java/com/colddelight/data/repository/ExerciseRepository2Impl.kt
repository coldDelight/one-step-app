package com.colddelight.data.repository

import com.colddelight.data.mapper.asDomain
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.HistoryEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.model.HistoryExerciseUI
import com.colddelight.model.SetInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ExerciseRepository2Impl @Inject constructor(
    private val historyDao: HistoryDao,
    private val historyExerciseDao: HistoryExerciseDao,
    private val routineDayDao: RoutineDayDao,
    private val dayExerciseDao: DayExerciseDao,
) : ExerciseRepository2 {

    private val todayHistoryId = historyDao.getHistoryForToday(LocalDate.now())


    private val dayOfWeek = LocalDate.now().dayOfWeek.value
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTodayExerciseList(): Flow<List<HistoryExerciseUI>> {
        return todayHistoryId.flatMapLatest {
            historyExerciseDao.getTodayHistoryExercises(it).map { it.asDomain() }
        }
    }
    override suspend fun upDateKgList(historyExerciseId: Int, kgList: List<Int>) {
        historyExerciseDao.updateKgList(historyExerciseId, kgList)
    }

    override suspend fun upDateRepsList(historyExerciseId: Int, repsList: List<Int>) {
        historyExerciseDao.updateRepsList(historyExerciseId, repsList)
    }

    override suspend fun upDateSetInfo(
        historyExerciseId: Int,
        kgList: List<Int>,
        repsList: List<Int>
    ) {
        historyExerciseDao.updateSetInfoList(historyExerciseId, kgList, repsList)
    }

    override suspend fun initExercise() {
        val id = todayHistoryId.firstOrNull() ?: -1

        when (id) {
            -1 -> {
                val todayRoutine = routineDayDao.getTodayRoutineDay(dayOfWeek).firstOrNull()
                if (todayRoutine != null) {
                    historyDao.insertHistory(
                        HistoryEntity(
                            LocalDate.now(),
                            todayRoutine.categoryList,
                            isDone = false,
                        )
                    )
                    val dayExercises =
                        dayExerciseDao.getDayExercisesByRoutineDayId(todayRoutine.id).first()
                    val historyExercises = dayExercises.map { dayExercise ->
                        HistoryExerciseEntity(
                            id = dayExercise.id,
                            historyId = todayHistoryId.firstOrNull() ?: -1,
                            exerciseId = dayExercise.exerciseId,
                            isDone = false,
                            kgList = dayExercise.kgList,
                            repsList = dayExercise.repsList,
                            dayExerciseId = dayExercise.id
                        )
                    }
                    historyExerciseDao.insertAll(historyExercises)
                }
            }

            else -> {}
        }
    }

    override suspend fun updateHistoryExercise(id: Int, isDone: Boolean) {
        historyExerciseDao.updateHistoryExercise(id, isDone)

    }

    override suspend fun updateDayExercise(id: Int, setInfoList: List<SetInfo>) {
        dayExerciseDao.updateKgRepsById(
            id,
            kgList = setInfoList.map { it.kg },
            repsList = setInfoList.map { it.reps })
    }

    override suspend fun finHistory() {
        historyDao.finToday(todayHistoryId.firstOrNull() ?: -1)
    }
}

