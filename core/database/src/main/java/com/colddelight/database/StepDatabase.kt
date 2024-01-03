package com.colddelight.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.colddelight.database.converter.DateConverter
import com.colddelight.database.converter.IntListConverter
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.database.model.RoutineEntity
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(
    entities = [
        RoutineEntity::class, RoutineDayEntity::class, DayExerciseEntity::class,
        ExerciseEntity::class,
        HistoryEntity::class, HistoryExerciseEntity::class,

    ],
    version = 7
)
@TypeConverters( DateConverter::class, IntListConverter::class)
abstract class StepDatabase : RoomDatabase() {
    companion object {
        fun getInstance(context: Context): StepDatabase = Room
            .databaseBuilder(context, StepDatabase::class.java, "step-database")
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        runBlocking {
                            getInstance(context).routineDao()
                                //루틴 초기값
                                .insertRoutine(RoutineEntity.DEFAULT_Routine)
                        }
                    }
                }
            })
            .build()
    }

    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineDao(): RoutineDao
    abstract fun routineDayDao(): RoutineDayDao
    abstract fun dayExerciseDao(): DayExerciseDao
    abstract fun historyDao(): HistoryDao
    abstract fun historyExerciseDao(): HistoryExerciseDao


}