package com.colddelight.data.repository

import android.util.Log
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.asExternalModel
import com.colddelight.model.Exercise
import com.colddelight.network.datasource.ExerciseDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val network: ExerciseDataSource,

) : ExerciseRepository {
    override fun getExerciseResourcesStream(): Flow<List<Exercise>> =
        exerciseDao.getExercise().map { it.map(ExerciseEntity::asExternalModel) }

    override suspend fun addItem() {
//        exerciseDao.delExercise(1)
//        exerciseDao.insertExercise(ExerciseEntity("스퀏","하체"))
//        network.getExercise()
        Log.e("TAG", "addItem: 아이템 ${network.getExercise()}", )
    }



}