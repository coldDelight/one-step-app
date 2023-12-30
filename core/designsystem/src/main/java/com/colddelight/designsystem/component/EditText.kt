package com.colddelight.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main

@Composable
fun EditText(data: String, focusManager: FocusManager, onChange: (Int) -> Unit) {
    BasicTextField(
        value = data,
        onValueChange = {
            if (it.length <= 3 && it.isNotBlank()) {
                onChange(it.toInt())
            }
        },
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(20.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        textStyle = HortaTypography.bodyMedium.copy(
            textAlign = TextAlign.Center,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Bottom,
                trim = LineHeightStyle.Trim.None
            )
        ),
        cursorBrush = SolidColor(Main)
    )
}

@Composable
fun EditTextKg(data: String, focusManager: FocusManager, onChange: (Int) -> Unit) {
    Box(
        Modifier
            .padding(horizontal = 8.dp)
            .background(Main, CircleShape)
            .padding(vertical = 6.dp, horizontal = 14.dp),
        Alignment.Center,
    ) {
        Row {
            BasicTextField(
                value = data,
                onValueChange = {
                    if (it.length <= 3 && it.isNotBlank()) {
                        onChange(it.toInt())
                    }
                },
                modifier = Modifier
                    .width(20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                textStyle = HortaTypography.bodyMedium.copy(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Bottom,
                        trim = LineHeightStyle.Trim.None
                    )
                ),
                cursorBrush = SolidColor(Main)
            )
            Text("kg", style = HortaTypography.bodyMedium, color = Color.White)
        }
    }
}