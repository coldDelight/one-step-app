package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayExerciseDao {


    @Query("SELECT * FROM day_exercise JOIN exercise ON day_exercise.exercise_id = exercise.id WHERE routine_day_id=(:routineDayId)")
    fun getAllDayExercise(routineDayId: Int): Flow<Map<DayExerciseEntity, ExerciseEntity>>


    @Query("SELECT * FROM day_exercise JOIN exercise ON day_exercise.exercise_id = exercise.id WHERE day_exercise.routine_day_id=(:routineDayId)")
    fun getDayExercise(routineDayId: Int): Flow<Map<DayExerciseEntity, ExerciseEntity>>

    @Query("SELECT * FROM day_exercise  WHERE day_exercise.routine_day_id=(:routineDayId)")
    fun getSimpleDayExercise(routineDayId: Int): Flow<List<DayExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayExercise(dayExerciseEntity: DayExerciseEntity)


    @Query("DELETE FROM day_exercise WHERE id = :dayExerciseId")
    suspend fun deleteDayExerciseById(dayExerciseId: Int)

//     fun getTodayExerciseList(routineDayId: Int): Flow<List<Exercise>> {
//        val historyIdFlow = historyDao.getTodayHistoryId(LocalDate.now())
//        return combine(
//            historyDao.getTodayHistoryId(LocalDate.now()).flatMapLatest { id ->
//                historyExerciseDao.getTodayHistoryExercises(id.keys.first())
//            },
//            dayExerciseDao.getDayExercise(routineDayId)
//        ) { historyExercises, dayExercises ->
//            //일단은 dayExercises만 가지고
//            //historyExercises가지고 히스토리 기준으로 나눠야 함
//            val combinedList = mutableListOf<Exercise>()
//            dayExercises.forEach { (dayExerciseEntity, exercise) ->
//                when (exercise.category) {
//                    ExerciseCategory.CALISTHENICS ->
//                        Exercise.Calisthenics(
//                            exerciseId = exercise.id,
//                            name = exercise.name,
//                            time = "",
//                            reps = dayExerciseEntity.repsList.maxOrNull() ?: 0,
//                            set = dayExerciseEntity.repsList.size
//                        )
//
//                    else ->
//                        Exercise.Weight(
//                            exerciseId = exercise.id,
//                            name = exercise.name,
//                            time = "",
//                            min = dayExerciseEntity.kgList.minOrNull() ?: 0,
//                            max = dayExerciseEntity.kgList.maxOrNull() ?: 0
//                        )
//                }.apply {
//                    combinedList.add(this)
//                }
//            }
//            combinedList
//        }
//    }

}