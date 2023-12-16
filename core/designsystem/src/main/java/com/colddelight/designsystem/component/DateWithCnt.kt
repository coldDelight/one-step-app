package com.colddelight.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main

@Composable
fun DateWithCnt(date: String, cnt: Int, modifier: Modifier = Modifier.padding(4.dp)) {
    Row(modifier = modifier) {
        Text(text = "$date [", style = HortaTypography.bodyMedium)
        Text(text = "$cnt", style = HortaTypography.bodyMedium, color = Main)
        Text(text = "]", style = HortaTypography.bodyMedium)
    }
}


@Preview
@Composable
fun DateWithCntPreview() {
    DateWithCnt("2023.11.05", 55)
}