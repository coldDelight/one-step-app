package com.colddelight.data.repository

import com.colddelight.data.mapper.asDomain
import com.colddelight.data.mapper.asEntity
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.model.ExerciseInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
) : ExerciseRepository {
    override suspend fun upsertExercise(exercise: ExerciseInfo) {
        exerciseDao.upsertExercise(exercise.asEntity())
    }

    override suspend fun deleteExercise(exerciseId: Int) {
        exerciseDao.deleteExerciseAndRelatedData(exerciseId)
    }

    override fun getAllExerciseList(): Flow<List<ExerciseInfo>> {
        return exerciseDao.getExercise().mapNotNull {
            it.asDomain()
        }
    }

}