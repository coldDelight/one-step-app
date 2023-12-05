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

public val IconPack.Homeselected: ImageVector
    get() {
        if (_homeselected != null) {
            return _homeselected!!
        }
        _homeselected = Builder(name = "Homeselected", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF1BC47D)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.0f, 22.0f)
                verticalLineTo(12.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(22.0f)
                moveTo(3.0f, 9.0f)
                lineTo(12.0f, 2.0f)
                lineTo(21.0f, 9.0f)
                verticalLineTo(20.0f)
                curveTo(21.0f, 20.5304f, 20.7893f, 21.0391f, 20.4142f, 21.4142f)
                curveTo(20.0391f, 21.7893f, 19.5304f, 22.0f, 19.0f, 22.0f)
                horizontalLineTo(5.0f)
                curveTo(4.4696f, 22.0f, 3.9609f, 21.7893f, 3.5858f, 21.4142f)
                curveTo(3.2107f, 21.0391f, 3.0f, 20.5304f, 3.0f, 20.0f)
                verticalLineTo(9.0f)
                close()
            }
        }
        .build()
        return _homeselected!!
    }

private var _homeselected: ImageVector? = null
