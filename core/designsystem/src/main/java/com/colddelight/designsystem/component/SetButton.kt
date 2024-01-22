package com.colddelight.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Homeunselected
import com.colddelight.designsystem.icons.iconpack.Minus
import com.colddelight.designsystem.icons.iconpack.Plus
import com.colddelight.designsystem.icons.iconpack.Trash
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.TextGray

@Composable
fun BigSetButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .background(DarkGray, shape = CircleShape)
            .size(38.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextGray,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun SmallSetButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .background(DarkGray, shape = CircleShape)
            .size(20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextGray,
            modifier = Modifier.size(10.dp)
        )
    }
}

@Composable
fun NoBackSetButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .size(20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = LightGray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
fun SetButtonPreview() {
    Column {
        BigSetButton(IconPack.Plus) {

        }
        BigSetButton(IconPack.Minus) {

        }
        NoBackSetButton(IconPack.Trash) {

        }
        SmallSetButton(Icons.Default.Add) {

        }
        SmallSetButton(Icons.Default.Add) {

        }
        SmallSetButton(Icons.Default.Add) {

        }
    }
}