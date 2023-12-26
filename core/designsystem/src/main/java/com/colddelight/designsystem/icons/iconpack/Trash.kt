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

public val IconPack.Trash: ImageVector
    get() {
        if (_trash != null) {
            return _trash!!
        }
        _trash = Builder(name = "Trash", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF565857)),
                strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(3.0f, 6.0f)
                horizontalLineTo(21.0f)
                moveTo(19.0f, 6.0f)
                verticalLineTo(20.0f)
                curveTo(19.0f, 21.0f, 18.0f, 22.0f, 17.0f, 22.0f)
                horizontalLineTo(7.0f)
                curveTo(6.0f, 22.0f, 5.0f, 21.0f, 5.0f, 20.0f)
                verticalLineTo(6.0f)
                moveTo(8.0f, 6.0f)
                verticalLineTo(4.0f)
                curveTo(8.0f, 3.0f, 9.0f, 2.0f, 10.0f, 2.0f)
                horizontalLineTo(14.0f)
                curveTo(15.0f, 2.0f, 16.0f, 3.0f, 16.0f, 4.0f)
                verticalLineTo(6.0f)
                moveTo(10.0f, 11.0f)
                verticalLineTo(17.0f)
                moveTo(14.0f, 11.0f)
                verticalLineTo(17.0f)
            }
        }
            .build()
        return _trash!!
    }

private var _trash: ImageVector? = null
