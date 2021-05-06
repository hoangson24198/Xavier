package com.hoangson.xavier.presentation.view.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoangson.xavier.R
import com.hoangson.xavier.presentation.compose.layout.GradientText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val SplashWaitTime: Long = 2000

@Composable
fun SplashView(
    modifier: Modifier = Modifier,
    onLauncherComplete: (LaunchDestination) -> Unit
) {
    val viewModel: SplashViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        val currentOnTimeout by rememberUpdatedState(onLauncherComplete)
        LaunchedEffect(Unit) {
            delay(SplashWaitTime)
            currentOnTimeout(viewState.launchDestination)
        }
        Timber.d("HSSplash")
        GradientText(mutableStateOf("X").value)
    }
}
