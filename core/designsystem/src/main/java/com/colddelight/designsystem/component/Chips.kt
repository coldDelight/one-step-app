package com.colddelight.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.TextGray

@Composable
fun CategoryChip(
    label : String,
    size: Int
){  Box(
        modifier = Modifier.padding(end=(size/2).dp)
    ) {
            Text(
                text = label,
                fontSize = size.sp,
                color = TextGray,
                modifier = Modifier
                    .border(1.dp, Main, CircleShape)
                    .padding(
                        start= size.dp,
                        end = size.dp,
                        top = (size/2).dp,
                        bottom = (size/2).dp
                    )
            )
    }
}

@Preview
@Composable
fun PreviewCategoryChip(){
    Row {
        CategoryChip(label = "Test",8)
        CategoryChip(label = "Test",16)
    }
}