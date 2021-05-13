package com.hoangson.xavier.presentation.compose.layout

import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flaviofaria.kenburnsview.KenBurnsView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.hoangson.xavier.R
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.hoangson.xavier.core.util.*
import com.hoangson.xavier.presentation.compose.animation.colorPropKey
import com.hoangson.xavier.presentation.ui.white30
import com.hoangson.xavier.presentation.ui.whiteGhost
import com.hoangson.xavier.presentation.ui.whiteGhost10
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


@Composable
fun rememberKenBurnsView(): KenBurnsView {
    val context = LocalContext.current
    return remember { KenBurnsView(context) }
}

@Composable
fun XavierCardView(modifier: Modifier, content: @Composable () -> Unit) = Card(elevation = 4.dp,
        modifier = modifier,
        backgroundColor = white30,
        shape = MaterialTheme.shapes.small) {
    Box(modifier = modifier,contentAlignment = Alignment.CenterStart) {
        content()
    }
}

@Composable
fun CardButton(text: String, icon: Int = -1, modifier: Modifier = Modifier, height: Int = 50, onClick: () -> Unit) =
        XavierCardView(modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onClick).height(height.dp)) {
            BoxWithConstraints {
                Box(modifier.fillMaxSize().border(1.dp, Color.White).background(Color.Transparent)) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize().padding(16.dp, 0.dp)) {
                        if (icon > 0) {
                            Image(painter = painterResource(id = icon), modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(Color.White),contentDescription = null)
                        }
                        Text(text = text, modifier = Modifier.fillMaxWidth(),
                            color = Color.White, textAlign = TextAlign.Center, style = MaterialTheme.typography.button)
                    }
                }
            }

        }

@Composable
fun rowSpacer(value: Int) = Spacer(modifier = Modifier.width(value.dp))

@Composable
fun columnSpacer(value: Int) = Spacer(modifier = Modifier.height(value.dp))

@Composable
fun ErrorView(message: String = "Oops! Something went wrong,\n Please refresh after some time!"){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_round_warning_24),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.error),
        modifier = Modifier.size(96.dp),contentDescription = null)
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
        Image(painter = painterResource(id = R.drawable.ic_empty_inbox_96),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface.copy(alpha = 0.8F)),
                modifier = Modifier.size(96.dp),contentDescription = null)
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

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

@Composable
fun OnBoardingSlide(selected: Boolean, color: Color, icon: ImageVector) {
    Icon(
        imageVector = icon,
        modifier = Modifier
            .padding(4.dp)
            .requiredSize(12.dp),
        contentDescription = null,
        tint = if (selected) color else Color.Gray
    )
}

@Composable
fun mutedVideoPlayer(url : String){
    val context = LocalContext.current
    val mediaItem : MediaItem = MediaItem.fromUri(Uri.parse(url))
    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)

            this.prepare(source)
        }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    DisposableEffect(AndroidView(factory = {
        PlayerView(context).apply {
            hideController()
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

            player = exoPlayer
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    })) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
fun VideoBackGround(url: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        //mutedVideoPlayer("asset:///sea.mp4")
        mutedVideoPlayer(url)
    }
}

@Composable
fun LoadingContent(
    loading: Boolean
) {
    if (loading) {
        Dialog(onDismissRequest = {}) {
            Box(
            modifier = Modifier
                .size(128.dp).background(whiteGhost10)
        ){
                CircleWave(modifier = Modifier.fillMaxSize())
            }
        }
        /*Box(
            modifier = Modifier
                .fillMaxSize().background(whiteGhost10)
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                CircleWave(modifier = Modifier.fillMaxSize())
            }
        }*/
    }
}
@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(it, modifier = Modifier.wrapContentWidth().align(Alignment.CenterVertically))
            }
        }
    }
}
