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

public val IconPack.Routineselected: ImageVector
    get() {
        if (_routineselected != null) {
            return _routineselected!!
        }
        _routineselected = Builder(name = "Routineselected", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF1BC47D)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(16.0f, 4.0f)
                horizontalLineTo(18.0f)
                curveTo(18.5304f, 4.0f, 19.0391f, 4.2107f, 19.4142f, 4.5858f)
                curveTo(19.7893f, 4.9609f, 20.0f, 5.4696f, 20.0f, 6.0f)
                verticalLineTo(20.0f)
                curveTo(20.0f, 20.5304f, 19.7893f, 21.0391f, 19.4142f, 21.4142f)
                curveTo(19.0391f, 21.7893f, 18.5304f, 22.0f, 18.0f, 22.0f)
                horizontalLineTo(6.0f)
                curveTo(5.4696f, 22.0f, 4.9609f, 21.7893f, 4.5858f, 21.4142f)
                curveTo(4.2107f, 21.0391f, 4.0f, 20.5304f, 4.0f, 20.0f)
                verticalLineTo(6.0f)
                curveTo(4.0f, 5.4696f, 4.2107f, 4.9609f, 4.5858f, 4.5858f)
                curveTo(4.9609f, 4.2107f, 5.4696f, 4.0f, 6.0f, 4.0f)
                horizontalLineTo(8.0f)
                moveTo(12.0f, 11.0f)
                horizontalLineTo(16.0f)
                moveTo(12.0f, 16.0f)
                horizontalLineTo(16.0f)
                moveTo(8.0f, 11.0f)
                horizontalLineTo(8.01f)
                moveTo(8.0f, 16.0f)
                horizontalLineTo(8.01f)
                moveTo(9.0f, 2.0f)
                horizontalLineTo(15.0f)
                curveTo(15.5523f, 2.0f, 16.0f, 2.4477f, 16.0f, 3.0f)
                verticalLineTo(5.0f)
                curveTo(16.0f, 5.5523f, 15.5523f, 6.0f, 15.0f, 6.0f)
                horizontalLineTo(9.0f)
                curveTo(8.4477f, 6.0f, 8.0f, 5.5523f, 8.0f, 5.0f)
                verticalLineTo(3.0f)
                curveTo(8.0f, 2.4477f, 8.4477f, 2.0f, 9.0f, 2.0f)
                close()
            }
        }
        .build()
        return _routineselected!!
    }

private var _routineselected: ImageVector? = null
