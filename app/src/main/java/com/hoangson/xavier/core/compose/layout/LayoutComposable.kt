package com.hoangson.xavier.core.compose.layout

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hoangson.xavier.R
import com.hoangson.xavier.core.compose.layout.*
import com.hoangson.xavier.core.enums.PageState
import java.util.Locale

@Composable
fun Chip(text: String, icon: Int = -1, onClick: () -> Unit) {
    Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp).clickable(onClick = { onClick() }),
            verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            if (icon != -1) {
                Image(modifier = Modifier.size(14.dp, 14.dp),painter = painterResource(id = icon), colorFilter = OnSurfaceTint(),contentDescription = null)
            }
            Spacer(Modifier.size(4.dp))
            Body2(text = text)
        }
    }
}

@Composable
fun OnSurfaceTint(): ColorFilter {
    return ColorFilter.tint(MaterialTheme.colors.onSurface)
}

@Composable
fun IconText(text: String, icon: Int = -1, onClick: () -> Unit = {}) {
    Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp).clickable(onClick = { onClick() }),
            verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != -1) {
            Image(modifier = Modifier.size(16.dp, 16.dp), painter = painterResource(id = icon), colorFilter = OnSurfaceTint(),contentDescription = null)
        }
        Spacer(Modifier.size(8.dp))
        Text(text = text, style = MaterialTheme.typography.subtitle2)
    }
}

/*@Composable
fun <T> LazyGridFor(
    items: List<T> = listOf(),
    rows: Int = 3,
    hPadding: Int = 8,
    itemContent: @Composable LazyItemScope.(T, Int) -> Unit
) {
    val animatedSet = remember { mutableSetOf<Int>() }
    val chunkedList = items.chunked(rows)
    LazyColumn(modifier = Modifier.padding(horizontal = hPadding.dp)) {
        itemsIndexed(chunkedList){index, item ->
            if (index == 0) {
                columnSpacer(value = 8)
            }
            val offsetValue = remember { Animatable(if(index in animatedSet) 0F else 150F)}
            val alphaValue = remember { Animatable(if(index in animatedSet) 1F else 0F)}
            SideEffect {
                offsetValue.animateTo(0F, animationSpec = tweenSpec)
                alphaValue.animateTo(1F, animationSpec = tweenSpec)
                animatedSet.add(index)
            }
            DisposableEffectScope().onDispose{
                offsetValue.snapTo(0F)
                offsetValue.stop()
            }
            Row(modifier = Modifier.offset(y = offsetValue.value.toInt().dp).drawOpacity(alphaValue.value)) {
                it.forEachIndexed { rowIndex, item ->
                    Box(modifier = Modifier.weight(1F).align(Alignment.Top).padding(8.dp), alignment = Alignment.Center) {
                        itemContent(item, index * rows + rowIndex)
                    }
                }
                repeat(rows - it.size) {
                    Box(modifier = Modifier.weight(1F).padding(8.dp)) {}
                }
            }
        }
    }
}*/

@Composable
fun Dialog(state: MutableState<Boolean>, title: String, desc: String, pText: String, onClick: () -> Unit) {
    if (state.value) {
        val bgColor = MaterialTheme.colors.surface
        AlertDialog(title = { H6(text = title) }, text = { Body2(text = desc) }, buttons = {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.End) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    TextButton(text = "CANCEL", modifier = Modifier.clickable(onClick = { state.value = false }))
                }
                rowSpacer(value = 16)
                TextButton(text = pText.toUpperCase(Locale.ENGLISH), modifier = Modifier.clickable(onClick = {
                    state.value = false
                    onClick()
                }))
            }
        }, onDismissRequest = { state.value = false }, backgroundColor = ElevationOverlay.apply(bgColor, 25.dp) ?: bgColor)
    }
}

@Composable
fun ColumnLine(modifier: Modifier = Modifier) {
    columnSpacer(value = 8)
    Divider(modifier = modifier.size((0.8).dp).fillMaxWidth(), color = MaterialTheme.colors.onSurface.copy(alpha = 0.3F))
    columnSpacer(value = 8)
}

@Composable
fun WithPageState(
    pageState: State<PageState>,
    IdleView: (@Composable () -> Unit) = { },
    errorMessage: String = "Oops! Something went wrong, Please try again after some time",
    emptyMessage: String = "Nothing in here Yet!, Please comeback later",
    content: @Composable () -> Unit
) {
    when (pageState.value) {
        PageState.LOADING -> {
            LoadingView()
        }
        PageState.ERROR -> {
            ErrorView(errorMessage)
        }
        PageState.DATA -> {
            content()
        }
        PageState.EMPTY -> {
            EmptyView(emptyMessage)
        }
        PageState.IDLE -> {
            IdleView.invoke()
        }
    }
}

@Composable
fun ToolBar(title: String, onBackClick: () -> Unit) {
    val bgColor = MaterialTheme.colors.surface
    TopAppBar(title = { H6(text = title, modifier = Modifier.offset(x = (-20).dp)) },
            navigationIcon = {
                Image(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),contentDescription = null, colorFilter = OnSurfaceTint(), modifier = Modifier.offset(x = 12.dp).clickable { onBackClick() })
            }, backgroundColor = ElevationOverlay.apply(bgColor, 4.dp) ?: bgColor)
}
