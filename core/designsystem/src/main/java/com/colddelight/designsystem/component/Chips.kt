package com.colddelight.designsystem.component

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.TextGray

@Composable
fun CategoryChip(
    isCanDelete: Boolean,
    label : String,
    categoryId: Int,
    size: Int,
    onDeleteClicked: (Int) -> Unit
){  Box(
        modifier = Modifier.padding(end=(size/2).dp)
    ) {
            Row(
                modifier = Modifier
                    .border(1.dp, Main, CircleShape)
                    .padding(
                        start = size.dp,
                        end = if(isCanDelete) (size/2).dp else size.dp,
                        top = (size / 2).dp,
                        bottom = (size / 2).dp
                    ),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = label,
                    fontSize = size.sp,
                    color = TextGray,
                    )
                if(isCanDelete)
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "삭제",
                        tint = DarkGray,
                        modifier = Modifier
                            .padding(start = (size/4).dp)
                            .size(18.dp)
                            .clickable {
                                onDeleteClicked(categoryId)
//                                Log.e(javaClass.simpleName, "CategoryChip: 이거 삭제 할게?", )
                            }
                    )
            }

    }
}

@Composable
fun FilterChip(text: String, onChipSelected: (Boolean) -> Unit){
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = TextGray,
            modifier = Modifier
                .border(1.dp, if (isSelected) Main else LightGray, CircleShape)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clickable {
                    isSelected = !isSelected
                    onChipSelected(isSelected)
                }
        )
    }
}



@Preview
@Composable
fun PreviewCategoryChip(){
    Row {
        CategoryChip(false,label = "Test",0,8,{})
        CategoryChip(true,label = "가슴",0,16,{})
    }
}