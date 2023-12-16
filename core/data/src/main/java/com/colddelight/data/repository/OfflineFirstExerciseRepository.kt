package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.SyncTask
import com.colddelight.database.dao.BodyWeightDao
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.model.Exercise
import com.colddelight.network.datasource.ExerciseDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class OfflineFirstExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val bodyWeightDao: BodyWeightDao,
    private val routineDao: RoutineDao,
    private val routineDayDao: RoutineDayDao,
    private val dayExerciseDao: DayExerciseDao,
    private val network: ExerciseDataSource,
    private val syncTask: SyncTask,

    ) : ExerciseRepository {
//    override fun getExerciseResourcesStream(): Flow<List<Exercise>> =
//        exerciseDao.getExercise().map { it.map(ExerciseEntity::asExercise) }

    override suspend fun addItem() {

//루틴 day 추가
//        routineDayDao.insertRoutineDay(RoutineDayEntity(1,1,"가슴,등"))
//        routineDayDao.insertRoutineDay(RoutineDayEntity(1,3,"하체"))
//        routineDayDao.insertRoutineDay(RoutineDayEntity(1,5,"어깨"))

        //exercise추가
//        exerciseDao.insertExercise(ExerciseEntity("벤치프레스","가슴"))
//        exerciseDao.insertExercise(ExerciseEntity("스쿼트","하체"))
//        exerciseDao.insertExercise(ExerciseEntity("데드리프트","하체"))

//        exerciseDao.insertExercise(ExerciseEntity("풀스쿼트","하체",2))

//dat exercise 추가
//        dayExerciseDao.insertDayExercise(DayExerciseEntity(1,1,1,"20,20","12,12"))
        Log.e("1", "bodyWeight: ${bodyWeightDao.getBodyWeight().first()}#")
        Log.e("2", "routine: ${routineDao.getAllRoutine().first()}#")
        Log.e("TAG", "routineDay: ${routineDayDao.getAllRoutineDay(1).first()}#")
        Log.e("TAG", "dayExercise: ${dayExerciseDao.getAllDayExercise(1).first()}#")
        Log.e("3", "exercise: ${exerciseDao.getExercise().first()}#")

//        exerciseDao.delExercise(1)
//        exerciseDao.insertExercise(ExerciseEntity("스퀏", "하체"))

//        syncTask. syncTask(4)


//        val uploadDataConstraints =
//            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
//        val workRequest =
//            OneTimeWorkRequestBuilder<CustomWorker>()
//                .setConstraints(uploadDataConstraints)
//                .build()
        //room에 저장
        //sync enque

//        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    override suspend fun addRoutine() {
        test(2) {
            routineDao.getRoutine(2)

        }
//        routineDao.insertRoutine(RoutineEntity("테스트 루틴", Date()))
//        syncTask.syncReq(4)
    }

    override suspend fun addRoutine(id: Int) {
    }


    override suspend fun delRoutine(id: Int) {
    }

    override suspend fun sync(id: Int) {
//        val roomData = exerciseDao.getExercise(id)
//        network.addExercise(roomData.asNetworkExercise())

    }

    suspend fun test(id: Int, syncFunction: suspend () -> Unit) {
        syncFunction.invoke()

    }


}