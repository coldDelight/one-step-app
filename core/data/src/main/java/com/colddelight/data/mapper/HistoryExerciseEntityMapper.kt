package com.colddelight.data.mapper

import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.HistoryExerciseUI
import com.colddelight.model.SetInfo

object HistoryExerciseEntityMapper {
    fun asDomain(entity: Map<HistoryExerciseEntity, ExerciseEntity>): List<HistoryExerciseUI> {
        return entity.map {
            HistoryExerciseUI(
                exercise = when (it.value.category) {
                    ExerciseCategory.CALISTHENICS ->
                        Exercise.Calisthenics(
                            exerciseId = it.value.id,
                            name = it.value.name,
                            reps = it.key.repsList.maxOrNull() ?: 0,
                            set = it.key.repsList.size,
                            category = it.value.category,
                            setInfoList = it.key.kgList.mapIndexed { index, kg ->
                                SetInfo(
                                    kg,
                                    it.key.repsList[index]
                                )
                            },
                            dayExerciseId = it.value.id

                        )

                    else -> Exercise.Weight(
                        exerciseId = it.value.id,
                        name = it.value.name,

                        min = it.key.kgList.minOrNull() ?: 0,
                        max = it.key.kgList.maxOrNull() ?: 0,
                        category = it.value.category,
                        setInfoList = it.key.kgList.mapIndexed { index, kg ->
                            SetInfo(
                                kg,
                                it.key.repsList[index]
                            )
                        },
                        dayExerciseId = it.key.id
                    )
                },
                isDone = it.key.isDone,
                id = it.key.id,
                //TODO 확인
                historyId = -1
            )
        }
    }

}


fun Map<HistoryExerciseEntity, ExerciseEntity>.asDomain(): List<HistoryExerciseUI> {
    return HistoryExerciseEntityMapper.asDomain(this)
}