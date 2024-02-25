package com.colddelight.data.repository

import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.model.DayExerciseWithExercise
import javax.inject.Inject

class HistoryExerciseRepositoryImpl @Inject constructor(
    private val historyExerciseDao: HistoryExerciseDao
) : HistoryExerciseRepository {

    override suspend fun deleteHistoryExercise(historyExerciseId: Int) {
        historyExerciseDao.deleteHistoryExerciseById(historyExerciseId)
    }

    //TODO dayExercise타입 historyExercise로 변경하기
    override suspend fun insertHistoryExercise(id:Int,todayHistoryId:Int,dayExercise: DayExerciseWithExercise) {
        val historyExerciseEntity = HistoryExerciseEntity(
            id = id,
            historyId = todayHistoryId,
            exerciseId = dayExercise.exerciseId,
            isDone = false,
            kgList = dayExercise.kgList,
            repsList = dayExercise.repsList,
            dayExerciseId = id
        )
        historyExerciseDao.insertHistoryExercise(historyExerciseEntity)
    }
}