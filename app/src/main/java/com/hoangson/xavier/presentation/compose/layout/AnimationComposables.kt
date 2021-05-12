package com.hoangson.xavier.presentation.compose.layout

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import com.hoangson.xavier.core.util.*
import com.hoangson.xavier.presentation.ui.*
import timber.log.Timber
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

@Composable
fun CircleWave(modifier: Modifier = Modifier) {
    val millis by animationTimeMillis()
    val path = remember { Path() }
    val colors = whiteBlueGradient

    Canvas(modifier = modifier) {
        drawRect(Color.Transparent)

        val (width, height) = size
        val waveAmplitude = size.minDimension / 60
        val circleRadius = size.minDimension / 5f - 9 * waveAmplitude

        Timber.d("HSCircleWave waveAmplitude = $waveAmplitude circleRadius = $circleRadius")

        translate(width / 2f, height / 2f) {
            colors.forEachIndexed { colorIndex, color ->
                path.reset()
                for (i in 0 until N) {
                    val a = i * TWO_PI / N
                    val t = millis * SPEED
                    val c = cos(a* FREQUENCY - colorIndex* SHIFT*t)
                    val p = ((1 + cos(a - t)) / 3).pow(3)
                    val r = circleRadius + waveAmplitude * c * p
                    val x = r * sin(a)
                    val y = r * -cos(a)
                    if (i == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }
                path.close()

                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(
                        width = 3.dp.toPx(),
                        join = StrokeJoin.Round
                    ),
                    blendMode = BlendMode.Screen,
                )
            }
        }
    }
}

@Composable
fun RingOfCircles(modifier: Modifier = Modifier) {
    val darkColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val lightColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    val millis by animationTimeMillis()
    Canvas(modifier = modifier) {
        val ringRadius = size.minDimension * 0.35f /7
        val waveRadius = size.minDimension * 0.10f /7
        val dotRadius = waveRadius / 4f
        val dotGap = dotRadius / 2f

        // Draw the dots below the ring.
        for (i in 0..NUM_DOTS) {
            drawDot(i, millis, true, ringRadius, waveRadius, dotRadius, dotGap, lightColor, lightColor)
        }

        // Draw the ring.
        drawCircle(lightColor, radius = ringRadius, style = Stroke(dotRadius + dotGap * 2))
        drawCircle(lightColor, radius = ringRadius, style = Stroke(dotRadius))

        // Draw the dots above the ring.
        for (i in 0..NUM_DOTS) {
            drawDot(i, millis, false, ringRadius, waveRadius, dotRadius, dotGap, lightColor, lightColor)
        }
    }
}

private fun DrawScope.drawDot(
    index: Int,
    millis: Long,
    below: Boolean,
    ringRadius: Float,
    waveRadius: Float,
    dotRadius: Float,
    dotGap: Float,
    ringColor: Color,
    outlineColor: Color,
) {
    val dotAngle = (index / NUM_DOTS.toFloat() + (millis / -DOT_PERIOD)) % 1f * TWO_PI
    val waveAngle = (dotAngle + (millis / -WAVE_PERIOD)) % TWO_PI

    if (cos(waveAngle) > 0 == below) {
        return
    }

    withTransform({
        rotate(dotAngle.toDegrees(), Offset(center.x, center.y))
        translate((ringRadius + sin(waveAngle) * waveRadius).toFloat(), 0f)
    }, {
        drawCircle(outlineColor, radius = dotRadius, style = Stroke(dotGap * 2))
        drawCircle(ringColor, radius = dotRadius)
    })
}