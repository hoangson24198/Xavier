package com.hoangson.xavier.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
    primary = Green500,
    secondary = Green500,
    onSecondary = Color.Black,
    surface = darkPrimary,
    background = background,
    onBackground = background800,
    primaryVariant = purple500,
    onPrimary = Color.Black,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    background = Color.White,
    onBackground = Color.White,
    surface = Color.White,
    primary = purple200,
    primaryVariant = purple500,
    secondary = purple500,
    onPrimary = Color.White,
    onSecondary = Color.White,
    error = orangeError
)

@Composable
fun XavierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    /**
     * support just dark color for this release.
     */
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    /**
     * support just dark color for this release.
     */
    val typography = if (darkTheme) {
        DarkTypography
    } else {
        LightTypography
    }

    MaterialTheme(
        colors = DarkColorPalette,
        typography = DelishTypography,
        shapes = shapes,
        content = content
    )
}
