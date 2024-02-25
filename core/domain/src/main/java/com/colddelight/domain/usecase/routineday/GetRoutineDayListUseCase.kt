package com.colddelight.domain.usecase.routineday

import com.colddelight.data.mapper.asExercise
import com.colddelight.data.repository.DayExerciseRepository
import com.colddelight.data.repository.RoutineDayRepository
import com.colddelight.model.RoutineDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetRoutineDayListUseCase @Inject constructor(
    private val routineDayRepository: RoutineDayRepository,
    private val dayExerciseRepository: DayExerciseRepository
) {
    operator fun invoke(): Flow<List<RoutineDay>> {
        val dayExerciseWithExerciseFlow = dayExerciseRepository.getDayExerciseList()
        val routineDayFlow = routineDayRepository.getRoutineDayList()

        return dayExerciseWithExerciseFlow.combine(routineDayFlow) { dayExerciseWithExerciseList, routineDayList ->
            val resultList = mutableListOf<RoutineDay>()
            val missingDays = (1..7).toSet() - routineDayList.map { it.dayOfWeek }.toSet()

            for (routineDay in routineDayList) {
                val exerciseList =  dayExerciseWithExerciseList.filter { it.routineDayId==routineDay.id }
                resultList.add(routineDay.copy(exerciseList = exerciseList.asExercise()))
            }

            missingDays.forEach { missingDay ->
                val nullRoutineDay = RoutineDay(
                    routineId = 1,//TODO 루틴아이디
                    dayOfWeek = missingDay,
                    categoryList = emptyList(),
                    exerciseList = emptyList()
                )
                resultList.add(nullRoutineDay)
            }
            resultList.sortedBy { it.dayOfWeek }
        }
    }
}