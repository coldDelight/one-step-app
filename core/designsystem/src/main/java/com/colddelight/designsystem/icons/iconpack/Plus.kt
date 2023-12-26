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

public val IconPack.Plus: ImageVector
    get() {
        if (_plus != null) {
            return _plus!!
        }
        _plus = Builder(name = "Plus", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFFC7C7C7)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.085f, 1.4004f)
                lineTo(9.085f, 14.6004f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 8.085f, 15.6004f)
                lineTo(7.9135f, 15.6004f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 6.9135f, 14.6004f)
                lineTo(6.9135f, 1.4004f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 7.9135f, 0.4004f)
                lineTo(8.085f, 0.4004f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 9.085f, 1.4004f)
                close()
            }
            path(fill = SolidColor(Color(0xFFC7C7C7)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.3994f, 6.915f)
                lineTo(14.5994f, 6.915f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 15.5994f, 7.915f)
                lineTo(15.5994f, 8.0865f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 14.5994f, 9.0865f)
                lineTo(1.3994f, 9.0865f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 0.3994f, 8.0865f)
                lineTo(0.3994f, 7.915f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 1.3994f, 6.915f)
                close()
            }
        }
        .build()
        return _plus!!
    }

private var _plus: ImageVector? = null
