package com.colddelight.designsystem.component

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Hamburger
import com.colddelight.designsystem.icons.iconpack.Topback
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepTopAppBar(
    @StringRes titleRes: Int,
    navigationType: TopAppBarNavigationType = TopAppBarNavigationType.Home,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    ){
    Column (){
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = titleRes),
                    color = Main
                ) },
            navigationIcon = {
                when(navigationType){
                    TopAppBarNavigationType.Home ->
                        Icon(
                            painter = painterResource(id = R.drawable.onestep_logo),
                            contentDescription = null,
                            modifier = Modifier.height(20.dp).padding(start = 12.dp),
                            tint = Main
                        )
                    TopAppBarNavigationType.Back -> IconButton(onClick = { onNavigationClick() }) {
                        Icon(
                            imageVector = IconPack.Topback,
                            contentDescription = null,
                        )
                    }
                    else -> {}
                }
            },
            actions = {
                when(navigationType){
                    TopAppBarNavigationType.Home ->
                        IconButton(
                            onClick = { onActionClick()
                            })
                        {
                        Icon(
                            imageVector = IconPack.Hamburger,
                            contentDescription = null,
                        )
                    }
                    else ->{}
                }

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BackGray,
                titleContentColor = Main,
                actionIconContentColor = TextGray,
                navigationIconContentColor = TextGray
            ))
        when(navigationType){
            TopAppBarNavigationType.Home -> {}
            else -> Spacer(modifier = Modifier
                .fillMaxWidth()
                .size(1.dp)
                .background(LightGray))

        }
    }
}

enum class TopAppBarNavigationType { Home, Back, Empty }

@Preview("Top App Bar")
@Composable
private fun StepAppBarPreview() {
    StepTopAppBar(
        titleRes = android.R.string.untitled,
    )
}
