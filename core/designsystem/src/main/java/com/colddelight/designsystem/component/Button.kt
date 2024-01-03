package com.colddelight.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.OneStepTheme
import com.colddelight.designsystem.theme.Red

@Composable
fun MainButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Main
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun SubButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkGray
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun RedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Red
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Preview
@Composable
fun StepButtonPreview() {
    OneStepTheme {
        Column {
            MainButton(onClick = {}, content = { Text("Test button") })
            SubButton(onClick = {}, content = { Text("Test button") })
            RedButton(onClick = {}, content = { Text("Test button") })
        }
    }
}