package com.colddelight.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.OneStepTheme

@Composable
fun StepButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Preview
@Composable
fun StepButtonPreview() {
    OneStepTheme {
        StepButton(onClick = {}, color = Main, content = { Text("Test button") })
    }
}