package com.colddelight.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Delete
import com.colddelight.designsystem.icons.iconpack.Minus
import com.colddelight.designsystem.icons.iconpack.Plus

@Composable
fun ExerciseDetailItem(
    kg: Int, reps: Int, index: Int,
    setAction: (SetAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NoBackSetButton(IconPack.Delete) {
                setAction(SetAction.DeleteSet(index))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SmallSetButton(IconPack.Minus) {
                    setAction(SetAction.UpdateKg(kg - 10, index))
                }
                EditTextKg(kg.toString(), focusManager) { newKg ->
                    setAction(SetAction.UpdateKg(newKg, index))
                }
                SmallSetButton(IconPack.Plus) {
                    setAction(SetAction.UpdateKg(kg + 10, index))
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SmallSetButton(IconPack.Minus) {
                    setAction(SetAction.UpdateReps(reps - 1, index))
                }
                EditText(reps.toString(), focusManager) { newReps ->
                    setAction(SetAction.UpdateReps(newReps, index))
                }
                SmallSetButton(IconPack.Plus) {
                    setAction(SetAction.UpdateReps(reps + 1, index))
                }
            }
        }
    }
}
sealed class SetAction {
    data class UpdateKg(val updatedKg: Int, val toChange: Int) : SetAction()
    data class UpdateReps(val updatedReps: Int, val toChange: Int) : SetAction()
    data class DeleteSet(val toChange: Int) : SetAction()
    data object AddSet : SetAction()
}
