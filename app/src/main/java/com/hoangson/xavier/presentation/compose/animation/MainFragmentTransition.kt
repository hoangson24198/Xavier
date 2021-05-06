package com.hoangson.xavier.presentation.compose.animation

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoangson.xavier.presentation.compose.animation.MainTransitionState.END
import com.hoangson.xavier.presentation.compose.animation.MainTransitionState.START

class TransitionData(
    index: Int,
    offset: Dp,
    alpha : Float
)
enum class MainTransitionState{
    START, END
}

@Composable
fun handleMainFragmentTransition(mainFragmentTransition: MainTransitionState) : TransitionData{
    val transition = updateTransition(mainFragmentTransition)
    val index : Int by animateIntAsState(
        targetValue = when(mainFragmentTransition){
            START -> 0
            END -> 6
        },
        animationSpec = keyframes {
            durationMillis = 600
            1 at 100
            2 at 200
            3 at 300
            4 at 400
            5 at 500
        }
    )
    val offset : Dp by animateDpAsState(
        targetValue = when(mainFragmentTransition){
            START -> 0.dp
            END -> (-150).dp
        },
        animationSpec = tween(durationMillis = 500, delayMillis = 1100, easing = FastOutLinearInEasing)
    )

    val alpha : Float by animateFloatAsState(
        targetValue = when(mainFragmentTransition){
            START -> 0F
            END -> 1F
        },
        animationSpec = tween(durationMillis = 1000, delayMillis = 1600, easing = LinearOutSlowInEasing)
    )
    return remember(transition){ TransitionData(index,offset,alpha) }
}