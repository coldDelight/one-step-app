package com.colddelight.designsystem.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.colddelight.designsystem.icons.iconpack.Close
import com.colddelight.designsystem.icons.iconpack.Hamburger
import com.colddelight.designsystem.icons.iconpack.Historyselected
import com.colddelight.designsystem.icons.iconpack.Historyunselected
import com.colddelight.designsystem.icons.iconpack.Homeselected
import com.colddelight.designsystem.icons.iconpack.Homeunselected
import com.colddelight.designsystem.icons.iconpack.Minuscircle
import com.colddelight.designsystem.icons.iconpack.Pluscircle
import com.colddelight.designsystem.icons.iconpack.Routineselected
import com.colddelight.designsystem.icons.iconpack.Routineunselected
import com.colddelight.designsystem.icons.iconpack.Topback
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Topback, Pluscircle, Historyunselected, Hamburger, Homeselected, Minuscircle,
        Routineselected, Close, Homeunselected, Routineunselected, Historyselected)
    return __AllIcons!!
  }
