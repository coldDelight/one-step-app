package com.colddelight.data.repository

import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.model.HistoryExercise
import com.colddelight.model.HistoryExerciseUI
import javax.inject.Inject

class HistoryExerciseRepositoryImpl @Inject constructor(
    private val historyExerciseDao: HistoryExerciseDao
) : HistoryExerciseRepository {

    override suspend fun deleteHistoryExercise(historyExerciseId: Int) {
        historyExerciseDao.deleteHistoryExerciseById(historyExerciseId)
    }

    override fun getHistoryExercise(historyId: Int) {
        TODO("Not yet implemented")
    }

    //TODO dayExercise타입 historyExercise로 변경하기
    override suspend fun insertHistoryExercise(historyExercise: HistoryExercise) {
        val historyExerciseEntity = HistoryExerciseEntity(
            id = historyExercise.id,
            historyId = historyExercise.historyId,
            exerciseId = historyExercise.exerciseId,
            dayExerciseId = historyExercise.dayExerciseId,
            isDone = historyExercise.isDone,
            kgList = historyExercise.kgList,
            repsList = historyExercise.repsList,
        )
        historyExerciseDao.insertHistoryExercise(historyExerciseEntity)
    }
}