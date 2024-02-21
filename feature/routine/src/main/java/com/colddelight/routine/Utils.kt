package com.colddelight.routine

import com.colddelight.designsystem.component.ui.SetAction
import com.colddelight.model.SetInfo
import java.util.Calendar

fun getDayOfWeekNumber(): Int {
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    return when (dayOfWeek) {
        Calendar.MONDAY -> 1
        Calendar.TUESDAY -> 2
        Calendar.WEDNESDAY -> 3
        Calendar.THURSDAY -> 4
        Calendar.FRIDAY -> 5
        Calendar.SATURDAY -> 6
        Calendar.SUNDAY -> 7
        else -> 0
    }
}

fun performSetAction(setInfoList: List<SetInfo>, action: SetAction): List<SetInfo> {
    when (action) {
        is SetAction.UpdateKg -> return upDateNewKgList(
            setInfoList,
            action.updatedKg,
            action.toChange
        )
        is SetAction.UpdateReps -> return upDateNewRepsList(
            setInfoList,
            action.updatedReps,
            action.toChange
        )
        is SetAction.DeleteSet -> return deleteSet(setInfoList, action.toChange)
        is SetAction.AddSet -> return addSet(setInfoList)
    }
}

fun upDateNewKgList(setInfoList: List<SetInfo>, updatedKg: Int, toChange: Int): List<SetInfo> {
    if (updatedKg > 0) {
        return setInfoList.mapIndexed { index, setInfo ->
            if (index >= toChange) {
                setInfo.copy(kg = updatedKg)
            } else {
                setInfo
            }
        }
    } else {
        return setInfoList
    }
}

fun upDateNewRepsList(setInfoList: List<SetInfo>, updatedReps: Int, toChange: Int): List<SetInfo> {
    return if (updatedReps > 0) {
        setInfoList.mapIndexed { index, setInfo ->
            if (index >= toChange) {
                setInfo.copy(reps = updatedReps)
            } else {
                setInfo
            }
        }
    } else {
        setInfoList
    }
}

fun deleteSet(setInfoList: List<SetInfo>, toChange: Int): List<SetInfo> {
    if (setInfoList.size == 1) {
        return setInfoList
    }
    val updatedSetInfoList = setInfoList.filterIndexed { index, _ -> index != toChange }
    return updatedSetInfoList
}

private fun addSet(setInfoList: List<SetInfo>): List<SetInfo> {
    val setInfoList = setInfoList.toMutableList()
    setInfoList.add(SetInfo(20, 12))
    return setInfoList
}