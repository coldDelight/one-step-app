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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main

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
            EditText(
                data, focusManager, HortaTypography.bodyMedium,
                modifier = Modifier
                    .width(20.dp), Color.White, onChange
            )
            Text("kg", style = HortaTypography.bodyMedium, color = Color.White)
        }
    }
}

@Composable
fun EditText(
    data: String,
    focusManager: FocusManager,
    style: TextStyle,
    modifier: Modifier,
    color: Color,
    onChange: (Int) -> Unit,
) {
    var text by remember { mutableStateOf(data) }
    BasicTextField(
        value = if (text.isEmpty()) text else data,
        onValueChange = {
            text = it
            if (it.length <= 3 && it.isNotBlank()) {
                onChange(it.toInt())
            }
        },
        modifier = modifier.onFocusChanged { if (!it.hasFocus) text = data },
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
        textStyle = style.copy(
            color = color,
            textAlign = TextAlign.Center,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Bottom,
                trim = LineHeightStyle.Trim.None
            )
        ),
        cursorBrush = SolidColor(color)
    )
}