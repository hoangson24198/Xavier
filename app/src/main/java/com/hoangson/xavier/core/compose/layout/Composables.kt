package com.hoangson.xavier.core.compose.layout

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.flaviofaria.kenburnsview.KenBurnsView
import com.hoangson.xavier.R
import com.hoangson.xavier.core.extension.load
import com.hoangson.xavier.core.ui.plasmaGradient
import com.rajankali.plasma.compose.animaton.ProgressColorTransition
import com.rajankali.plasma.compose.animaton.colorPropKey

@Suppress("unused")
@Composable
fun KenBurnsView(url: String, modifier: Modifier) {
    val kenBuns =  rememberKenBurnsView()
    AndroidView({ kenBuns }, modifier = modifier) {
        it.load(url = url)
    }
}

@Composable
fun rememberKenBurnsView(): KenBurnsView {
    val context = LocalContext.current
    return remember { KenBurnsView(context) }
}

@Composable
fun PlasmaCardView(modifier: Modifier, content: @Composable () -> Unit) = Card(elevation = 4.dp,
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.small) {
    Box(contentAlignment = Alignment.CenterStart) {
        content()
    }
}

@Composable
fun CardButton(text: String, icon: Int = -1, modifier: Modifier = Modifier, height: Int = 50, onClick: () -> Unit) =
        PlasmaCardView(modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onClick).height(height.dp)) {
            BoxWithConstraints {
                Box(modifier.fillMaxWidth().fillMaxHeight().background(Brush.horizontalGradient(plasmaGradient, 0F, constraints.maxWidth.toFloat(), TileMode.Clamp))) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp, 0.dp)) {
                        if (icon > 0) {
                            Image(painter = painterResource(id = icon),contentDescription = null, modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(Color.White))
                        }
                        Text(text = text, modifier = Modifier.fillMaxWidth(),
                            color = Color.White, textAlign = TextAlign.Center, style = MaterialTheme.typography.button)
                    }
                }
            }

        }

@Suppress("unused")
@Composable
fun rowSpacer(value: Int) = Spacer(modifier = Modifier.size(value.dp))

@Composable
fun columnSpacer(value: Int) = Spacer(modifier = Modifier.size(value.dp))

@Composable
fun LoadingView(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        val colorState = transition(
            definition = ProgressColorTransition,
            initState = 0,
            toState = 1
        )
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally), color = colorState[colorPropKey])
    }
}

@Composable
fun ErrorView(message: String = "Oops! Something went wrong,\n Please refresh after some time!"){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_round_warning_24),null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.error),
        modifier = Modifier.size(96.dp))
        columnSpacer(value = 12)
        ErrorText(message)
    }
}

@Composable
fun EmptyView(message: String = "Nothing in here Yet!, Please comeback later"){
    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_empty_inbox_96),null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface.copy(alpha = 0.8F)),
                modifier = Modifier.size(96.dp))
        columnSpacer(value = 12)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            EmptyText(message)
        }
    }
}

@Composable
fun Toast(message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(LocalContext.current, message, length).show()
}

/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

/**
 * Calculates the color of an elevated `surface` in dark mode. Returns `surface` in light mode.
 */
@Composable
fun Colors.elevatedSurface(elevation: Dp): Color {
    return ElevationOverlay.apply(
        color = this.surface,
        elevation = elevation
    ) ?: this.surface
}