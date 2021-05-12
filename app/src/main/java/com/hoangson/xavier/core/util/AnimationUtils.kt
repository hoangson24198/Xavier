package com.hoangson.xavier.core.util

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.whenStarted
import kotlin.math.pow
import kotlin.math.sin

const val N = 360
const val SPEED = 3f / 1000f
const val SHIFT = TWO_PI / 3f
const val FREQUENCY = 8

const val NUM_DOTS = 16
const val DOT_PERIOD = 10000
const val WAVE_PERIOD = DOT_PERIOD / (8 * Math.PI)

const val NUM_DOTS_GLOBAL = 1000
const val GLOBE_RADIUS_FACTOR = 0.7f
const val DOT_RADIUS_FACTOR = 0.005f
const val FIELD_OF_VIEW_FACTOR = 0.8f

@Composable
fun animationTimeMillis(): State<Long> {
    val millisState = remember {mutableStateOf(0L)}
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(true) {
        val startTime = withFrameMillis { it }
        lifecycleOwner.whenStarted {
            while (true) {
                withFrameMillis { frameTime ->
                    millisState.value = frameTime - startTime
                }
            }
        }
    }
    return millisState
}

fun sinebow(t: Float): Color {
    return Color(
        red = sin(PI * (t + 0f / 3f)).pow(2),
        green = sin(PI * (t + 1f / 3f)).pow(2),
        blue = sin(PI * (t + 2f / 3f)).pow(2),
    )
}
