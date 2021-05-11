package com.hoangson.xavier.presentation.compose.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoangson.xavier.presentation.compose.animation.MainTransitionState.START
import com.hoangson.xavier.presentation.ui.whiteBlueGradient

enum class MainTransitionState {
    START, END
}

@Composable
fun titleOffsetPropkey(state: MainTransitionState,value : Int): Dp {
    val transition: Transition<MainTransitionState> = updateTransition(state)
    val offset: Dp by transition.animateDp(transitionSpec = {tween(
        durationMillis = 500,
        delayMillis = 1100,
        easing = FastOutLinearInEasing
    )},label = ""){state ->
        if (state == START) 0.dp else value.dp
    }
    return offset
}


@Composable
fun titleIndexPropkey(state: MainTransitionState): Int {
    val transition: Transition<MainTransitionState> = updateTransition(state)
    val index: Int by transition.animateInt(transitionSpec = {
        keyframes {
            durationMillis = 600
            1 at 100
            2 at 200
            3 at 300
            4 at 400
            5 at 500
        }
    }, label = "") { state ->
        if (state == START) 0 else 6
    }
    return index
}

@Composable
fun titleAlphaPropkey(state: MainTransitionState): Float {
    val transition: Transition<MainTransitionState> = updateTransition(state)
    val alpha: Float by transition.animateFloat(transitionSpec = {
        tween(
            durationMillis = 1000,
            delayMillis = 1600,
            easing = LinearOutSlowInEasing
        )
    }, label = "") { state ->
        if (state == START) 0F else 1F
    }
    return alpha
}

@Composable
fun colorPropKey(state : Boolean) : Color {
    val transition : Transition<Boolean> = updateTransition(state)
    val color by transition.animateColor(transitionSpec = {
        repeatable(
            iterations = 5000,
            animation = keyframes {
                durationMillis = 300
                whiteBlueGradient[1] at 100
            }
        )
    },label = ""){ state ->
        if (state) whiteBlueGradient[0] else whiteBlueGradient[2]
    }
    return color
}