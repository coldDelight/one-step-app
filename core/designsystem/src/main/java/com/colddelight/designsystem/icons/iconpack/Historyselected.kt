package com.colddelight.designsystem.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.icons.IconPack

public val IconPack.Historyselected: ImageVector
    get() {
        if (_historyselected != null) {
            return _historyselected!!
        }
        _historyselected = Builder(name = "Historyselected", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF1BC47D)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(3.0f, 3.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(21.0f)
                moveTo(18.0f, 17.0f)
                verticalLineTo(9.0f)
                moveTo(13.0f, 17.0f)
                verticalLineTo(5.0f)
                moveTo(8.0f, 17.0f)
                verticalLineTo(14.0f)
            }
        }
        .build()
        return _historyselected!!
    }

private var _historyselected: ImageVector? = null
