package com.colddelight.exercisedetail

import androidx.lifecycle.ViewModel
import com.colddelight.data.repository.ExerciseRepository2
import com.colddelight.domain.usecase.routine.GetRoutineUseCase
import com.colddelight.domain.usecase.routine.GetTodayRoutineUseCase
import com.colddelight.domain.usecase.routine.UpsertRoutineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    getTodayRoutineUseCase: GetTodayRoutineUseCase

) : ViewModel() {

}