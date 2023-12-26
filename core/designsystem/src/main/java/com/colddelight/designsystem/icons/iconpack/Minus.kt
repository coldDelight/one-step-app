package com.colddelight.designsystem.icons.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.icons.IconPack

public val IconPack.Minus: ImageVector
    get() {
        if (_minus != null) {
            return _minus!!
        }
        _minus = Builder(name = "Minus", defaultWidth = 16.0.dp, defaultHeight = 4.0.dp,
                viewportWidth = 16.0f, viewportHeight = 4.0f).apply {
            path(fill = SolidColor(Color(0xFFC7C7C7)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.4346f, 0.1489f)
                lineTo(14.6782f, 0.1489f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 15.6782f, 1.1489f)
                lineTo(15.6782f, 2.4154f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 14.6782f, 3.4154f)
                lineTo(1.4346f, 3.4154f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 0.4346f, 2.4154f)
                lineTo(0.4346f, 1.1489f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 1.4346f, 0.1489f)
                close()
            }
        }
        .build()
        return _minus!!
    }

private var _minus: ImageVector? = null
