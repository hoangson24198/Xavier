package com.hoangson.xavier.presentation.compose.layout

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.hoangson.xavier.R
import com.hoangson.xavier.presentation.ui.whiteBlueGradient
import com.hoangson.xavier.presentation.ui.whiteGhostGradient

@Composable
fun Body(text: String, modifier: Modifier = Modifier) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
        Text(text = text, modifier = modifier, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun TextButton(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.button)
}

@Composable
fun Link(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, color = MaterialTheme.colors.primaryVariant,
        modifier = modifier,
        style = MaterialTheme.typography.button, textDecoration = TextDecoration.Underline
    )
}

@Composable
fun Body2(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.body2)
}

@Composable
fun CenteredBody(text: String, modifier: Modifier = Modifier) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
        Text(
            text = text,
            modifier = modifier,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Caption(text: String, modifier: Modifier = Modifier) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(text = text, modifier = modifier, style = MaterialTheme.typography.caption)
    }
}

@Composable
fun CenteredCaption(text: String, modifier: Modifier = Modifier) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
        Text(
            text = text,
            modifier = modifier,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Title(textSate: MutableState<String> = mutableStateOf(""), modifier: Modifier = Modifier) {
    val titleFontFamily = FontFamily(listOf(Font(resId = R.font.montserrat_regular)))
    val titleTextStyle = TextStyle(fontFamily = titleFontFamily, fontSize = 50.sp)
    Text(
        text = textSate.value, modifier = modifier,
        style = titleTextStyle, color = MaterialTheme.colors.primary
    )
}

@Composable
fun H6(text: String, modifier: Modifier = Modifier, color: Color) {
    Text(text = text, style = MaterialTheme.typography.h6, modifier = modifier, color = color)
}

@Composable
fun H1(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.h1, modifier = modifier)
}

@Composable
fun H2(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.h2, modifier = modifier)
}

@Composable
fun ErrorText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, modifier = modifier.fillMaxWidth().padding(horizontal = 48.dp),
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center, color = MaterialTheme.colors.error.copy(alpha = 0.9F)
    )
}

@Composable
fun EmptyText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, modifier = modifier.fillMaxWidth().padding(horizontal = 48.dp),
        style = MaterialTheme.typography.body2,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun GradientText(
    name: String,
    size: Float = 130F,
    colors: List<Color> = whiteBlueGradient,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val paint = Paint().asFrameworkPaint()
    BoxWithConstraints {
        val y = size / 2
        val gradientShader: Shader = LinearGradientShader(
            from = Offset(0f, 0f),
            to = Offset(0F, size / 2),
            colors
        )
        Canvas(modifier = modifier) {
            paint.apply {
                isAntiAlias = true
                textSize = size
                typeface = ResourcesCompat.getFont(context, R.font.logo)
                style = android.graphics.Paint.Style.FILL
                color = android.graphics.Color.parseColor("#cdcdcd")
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
            }
            val x = -paint.measureText(name) / 2
            drawIntoCanvas { canvas ->
                canvas.save()
                canvas.nativeCanvas.drawText(name, x, y, paint)
                canvas.restore()
                paint.shader = gradientShader
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                paint.maskFilter = null
                canvas.nativeCanvas.drawText(name, x, y, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
                canvas.nativeCanvas.drawText(name, x, y, paint)
            }
            paint.reset()
        }
    }
}