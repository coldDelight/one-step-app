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

public val IconPack.Day: ImageVector
    get() {
        if (_day != null) {
            return _day!!
        }
        _day = Builder(
            name = "Day", defaultWidth = 21.0.dp, defaultHeight = 18.0.dp, viewportWidth
            = 21.0f, viewportHeight = 18.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFC7C7C7)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(1.0339f, 13.5199f)
                lineTo(1.6475f, 10.8608f)
                curveTo(2.034f, 9.1862f, 3.5251f, 8.0f, 5.2436f, 8.0f)
                curveTo(7.6539f, 8.0f, 9.4173f, 10.2726f, 8.8186f, 12.6073f)
                lineTo(8.1408f, 15.2508f)
                curveTo(7.726f, 16.8687f, 6.268f, 18.0f, 4.5978f, 18.0f)
                curveTo(2.2457f, 18.0f, 0.505f, 15.8119f, 1.0339f, 13.5199f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFC7C7C7)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.9093f, 13.6355f)
                lineTo(13.1579f, 2.8422f)
                curveTo(13.5028f, 1.1866f, 14.962f, 0.0f, 16.6533f, 0.0f)
                curveTo(18.9036f, 0.0f, 20.5928f, 2.0565f, 20.1557f, 4.2639f)
                lineTo(18.0143f, 15.079f)
                curveTo(17.6781f, 16.7767f, 16.1888f, 18.0f, 14.4582f, 18.0f)
                curveTo(12.1587f, 18.0f, 10.4403f, 15.8867f, 10.9093f, 13.6355f)
                close()
            }
        }
            .build()
        return _day!!
    }

private var _day: ImageVector? = null
