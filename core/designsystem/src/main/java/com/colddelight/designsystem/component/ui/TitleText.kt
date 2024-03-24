package com.colddelight.designsystem.component.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(20.dp)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize(),
                onDraw = {
                    drawLine(
                        color = Main,
                        start = Offset(size.width / 2, 0f),
                        end = Offset(size.width / 2, size.height),
                        strokeWidth = 3.dp.toPx(),
                    )
                }
            )
        }
        Text(text, style = HortaTypography.headlineSmall, modifier = Modifier.padding(start = 3.dp))
    }
}


@Preview
@Composable
fun TitleTextPreview() {
    TitleText("Today")
}