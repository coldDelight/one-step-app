package com.colddelight.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.database.StepDatabase
import com.colddelight.database.dao.ExerciseDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@HiltWorker
class CustomWorker @AssistedInject constructor(
//    private val exerciseRepository: ExerciseRepository,
    @Assisted appContext: Context,
//    private val exerciseDao: ExerciseDao,
//    private val network: ExerciseDataSource,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try{
            val id = inputData.getInt("id", 0)
//            exerciseRepository.sync(id)
            Result.success()
        }catch (e:Exception){
            Result.failure()
        }

    }
}
