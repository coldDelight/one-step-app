package com.colddelight.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Delete
import com.colddelight.designsystem.icons.iconpack.Minus
import com.colddelight.designsystem.icons.iconpack.Plus
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.TextGray

@Composable
fun ExerciseDetailItem(
    kg: Int, reps: Int, index: Int,
    focusManager: FocusManager = LocalFocusManager.current,
    setAction: (SetAction) -> Unit,
) {
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.2f)
            ) {
                NoBackSetButton(IconPack.Delete) {
                    setAction(SetAction.DeleteSet(index))
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.8f)

            ) {
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
                    horizontalArrangement = Arrangement.Center,
                ) {
                    SmallSetButton(IconPack.Minus) {
                        setAction(SetAction.UpdateReps(reps - 1, index))
                    }
                    EditText(
                        reps.toString(), focusManager, HortaTypography.bodyMedium, Modifier
                            .padding(horizontal = 8.dp)
                            .width(20.dp),
                        color = TextGray
                    ) { newReps ->
                        setAction(SetAction.UpdateReps(newReps, index))
                    }

                    SmallSetButton(IconPack.Plus) {
                        setAction(SetAction.UpdateReps(reps + 1, index))
                    }
                }
            }


        }
    }
}

@Composable
fun DoneExerciseDetailItem(
    kg: Int, reps: Int
) {
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                Modifier
                    .padding(horizontal = 8.dp)
                    .background(Main, CircleShape)
                    .padding(vertical = 6.dp, horizontal = 14.dp),
                Alignment.Center,
            ) {
                Text(
                    "${kg}kg",
                    modifier = Modifier
                        .width(30.dp),
                    style = HortaTypography.bodyMedium.copy(
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Bottom,
                            trim = LineHeightStyle.Trim.None
                        )
                    ),
                )
            }
            Spacer(Modifier)
            Text(
                "$reps", modifier = Modifier
                    .width(30.dp), style = HortaTypography.bodyMedium
            )

        }
    }
}


sealed class SetAction {
    data class UpdateKg(val updatedKg: Int, val toChange: Int) : SetAction()
    data class UpdateReps(val updatedReps: Int, val toChange: Int) : SetAction()
    data class DeleteSet(val toChange: Int) : SetAction()
    data object AddSet : SetAction()
}
