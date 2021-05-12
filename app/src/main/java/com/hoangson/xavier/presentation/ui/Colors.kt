package com.hoangson.xavier.presentation.ui

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val purple200 = Color(0xFF651FFF)
val purple500 = Color(0xFF6200EA)
val background = Color(0xFF2B292B)
val background800 = Color(0xFF424242)
val background900 = Color(0xFF212121)
val white87 = Color(0Xddffffff)
val white30 = Color(0X4dffffff)
val white = Color(0xffffffff)
val teal200 = Color(0XFF68E4DC)
val silver = Color(0xFFC0C0C0)

val darkPrimary = Color(0xff242316)

val blue200 = Color(0xff91a4fc)
val Green500 = Color(0xFF1EB980)
val DarkBlue900 = Color(0xFF26282F)
val orangeError = Color(0xFFF94701)
val whiteGhost = Color(0xFFF8F8FF)
val whiteGhost10 = Color(0x1AF8F8FF)
val loading1 = Color(0xFF0294A5)
val loading2 = Color(0xFFF3E96B)
val loading3 = Color(0xFFF05837)


val String.color
    get() = Color(android.graphics.Color.parseColor(this))
val whiteBlueGradient = listOf(teal200,"#FF56bee0".color, blue200)

val whiteGhostGradient = listOf(whiteGhost, white87, white)

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

@Composable
fun Colors.randomBackgroundColor(): Color {
    val colors: List<Color> = listOf(
        Color(0xFFD0EFB3),
        Color(0xFFC0D6E3),
        Color(0xFFD8D8D8),
        Color(0xFFEE7B7E),
        Color(0xFFFFD48F),
        Color(0xFFD8D8D8)
    )

    return colors.random()
}
