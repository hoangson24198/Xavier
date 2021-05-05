package com.hoangson.xavier.core.compose.layout

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun GlideImage(
    modifier: Modifier = Modifier.fillMaxSize(),
    model: Any,
    onImageReady: (() -> Unit)? = null,
    customize: RequestBuilder<Bitmap>.() -> RequestBuilder<Bitmap> = { this }
) {
    Box(modifier = modifier) {
        BoxWithConstraints {
            val image = mutableStateOf<ImageBitmap?>(null)
            val drawable = mutableStateOf<Drawable?>(null)
            val context = LocalContext.current

            SideEffect{
                val glide = Glide.with(context)
                var target: CustomTarget<Bitmap>? = null
                val job = CoroutineScope(Dispatchers.Main).launch {
                    target = object : CustomTarget<Bitmap>() {
                        override fun onLoadCleared(placeholder: Drawable?) {
                            image.value = null
                            drawable.value = placeholder
                        }

                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            image.value = resource.asImageBitmap()
                            onImageReady?.invoke()
                        }
                    }

                    val width = if (constraints.maxWidth > 0 && constraints.maxWidth < Int.MAX_VALUE) {
                        constraints.maxWidth
                    } else {
                        SIZE_ORIGINAL
                    }

                    val height = if (constraints.maxHeight > 0 && constraints.maxHeight < Int.MAX_VALUE) {
                        constraints.maxHeight
                    } else {
                        SIZE_ORIGINAL
                    }

                    glide.asBitmap().load(model)
                            .override(width, height)
                            .let(customize)
                            .into(target!!)
                }

                DisposableEffectScope().onDispose{
                    image.value = null
                    drawable.value = null
                    glide.clear(target)
                    job.cancel()
                }
            }

            val theImage = image.value
            val theDrawable = drawable.value
            if (theImage != null) {
                Image(bitmap = theImage,null)
            } else if (theDrawable != null) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawIntoCanvas { theDrawable.draw(it.nativeCanvas) }
                }
            }
        }
    }
}
