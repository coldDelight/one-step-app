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

public val IconPack.Delete: ImageVector
    get() {
        if (_delete != null) {
            return _delete!!
        }
        _delete = Builder(
            name = "Delete", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
            viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF565857)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(6.3643f, 4.9493f)
                lineTo(14.8496f, 13.4346f)
                arcTo(1.0f, 1.0f, 60.9454f, false, true, 14.8496f, 14.8488f)
                lineTo(14.8496f, 14.8488f)
                arcTo(1.0f, 1.0f, 60.9454f, false, true, 13.4354f, 14.8488f)
                lineTo(4.9501f, 6.3635f)
                arcTo(1.0f, 1.0f, 118.155f, false, true, 4.9501f, 4.9493f)
                lineTo(4.9501f, 4.9493f)
                arcTo(1.0f, 1.0f, 118.155f, false, true, 6.3643f, 4.9493f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF565857)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(4.9493f, 13.435f)
                lineTo(13.4346f, 4.9497f)
                arcTo(1.0f, 1.0f, 77.6488f, false, true, 14.8488f, 4.9497f)
                lineTo(14.8488f, 4.9497f)
                arcTo(1.0f, 1.0f, 81.4349f, false, true, 14.8488f, 6.3639f)
                lineTo(6.3635f, 14.8492f)
                arcTo(1.0f, 1.0f, 132.5151f, false, true, 4.9493f, 14.8492f)
                lineTo(4.9493f, 14.8492f)
                arcTo(1.0f, 1.0f, 77.6488f, false, true, 4.9493f, 13.435f)
                close()
            }
        }
            .build()
        return _delete!!
    }

private var _delete: ImageVector? = null
