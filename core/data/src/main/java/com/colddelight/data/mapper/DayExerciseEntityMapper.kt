package com.colddelight.data.mapper

import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.model.DayExerciseWithExercise
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.SetInfo

object DayExerciseEntityMapper {

    fun asEntity(domain: DayExerciseWithExercise): DayExerciseEntity {
        return DayExerciseEntity(
            routineDayId = domain.routineDayId,
            exerciseId = domain.exerciseId,
            kgList = domain.kgList,
            repsList = domain.repsList,
            id = domain.id,
        )
    }

    fun asEntity(domain: List<DayExerciseWithExercise>): List<DayExerciseEntity> {
        return domain.map { routineDay ->
            routineDay.asEntity()
        }
    }

    fun asExercise(dayExerciseWithExercise: List<DayExerciseWithExercise>): List<Exercise> {
        return dayExerciseWithExercise.map {
            it.asExercise()
        }
    }

    fun asExercise(dayExerciseWithExercise: DayExerciseWithExercise): Exercise {
        return when (dayExerciseWithExercise.category) {
            ExerciseCategory.CALISTHENICS ->
                Exercise.Calisthenics(
                    exerciseId = dayExerciseWithExercise.id,
                    name = dayExerciseWithExercise.name,
                    reps = dayExerciseWithExercise.repsList.maxOrNull() ?: 0,
                    set = dayExerciseWithExercise.repsList.size,
                    category = dayExerciseWithExercise.category,
                    setInfoList = dayExerciseWithExercise.kgList.mapIndexed { index, kg ->
                        SetInfo(
                            kg,
                            dayExerciseWithExercise.repsList[index]
                        )
                    },
                    dayExerciseId = dayExerciseWithExercise.id

                )

            else -> Exercise.Weight(
                exerciseId = dayExerciseWithExercise.id,
                name = dayExerciseWithExercise.name,
                min = dayExerciseWithExercise.kgList.minOrNull() ?: 0,
                max = dayExerciseWithExercise.kgList.maxOrNull() ?: 0,
                category = dayExerciseWithExercise.category,
                setInfoList = dayExerciseWithExercise.kgList.mapIndexed { index, kg ->
                    SetInfo(
                        kg,
                        dayExerciseWithExercise.repsList[index]
                    )
                },
                dayExerciseId = dayExerciseWithExercise.id

            )
        }
    }

    fun asDomain(entity: Map<DayExerciseEntity, ExerciseEntity>): List<DayExerciseWithExercise> {
        return entity.map {
            DayExerciseWithExercise(
                id = it.key.id,
                routineDayId = it.key.routineDayId,
                exerciseId = it.key.exerciseId,
                kgList = it.key.kgList,
                repsList = it.key.repsList,
                name = it.value.name,
                category = it.value.category,
            )
        }
    }

}

fun List<DayExerciseWithExercise>.asEntity(): List<DayExerciseEntity> {
    return DayExerciseEntityMapper.asEntity(this)
}

fun DayExerciseWithExercise.asEntity(): DayExerciseEntity {
    return DayExerciseEntityMapper.asEntity(this)
}

fun List<DayExerciseWithExercise>.asExercise(): List<Exercise> {
    return DayExerciseEntityMapper.asExercise(this)
}

fun DayExerciseWithExercise.asExercise(): Exercise {
    return DayExerciseEntityMapper.asExercise(this)
}

fun Map<DayExerciseEntity, ExerciseEntity>.asDomain(): List<DayExerciseWithExercise> {
    return DayExerciseEntityMapper.asDomain(this)
}


