package com.hoangson.xavier.presentation.view.splash

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import com.hoangson.xavier.R
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.core.util.checkAllMatched
import com.hoangson.xavier.core.util.navigateSafely
import com.hoangson.xavier.presentation.compose.layout.GradientText
import com.hoangson.xavier.presentation.ui.whiteGhostGradient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
    }

    @Composable
    override fun setContent() {
        super.setContent()
        SplashView(
            onLauncherComplete = { destination ->
                when (destination) {
                    LaunchDestination.MAIN_ACTIVITY -> {
                        navController.navigateSafely(
                            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                        )
                    }
                    LaunchDestination.AUTH_ACTIVITY -> {
                        navController.navigateSafely(SplashFragmentDirections.actionSplashFragmentToAuthFragment())
                    }

                    LaunchDestination.ONBOARD -> {
                        navController.navigateSafely(SplashFragmentDirections.actionSplashFragmentToOnBoardFragment())
                    }
                }.checkAllMatched
            }
        )
    }

    @Composable
    fun SplashView(
        modifier: Modifier = Modifier,
        onLauncherComplete: (LaunchDestination) -> Unit
    ) {
        val viewModel = ViewModelProvider(requireActivity()).get(SplashViewModel::class.java)
        val viewState by viewModel.state.collectAsState()
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            val currentOnTimeout by rememberUpdatedState(onLauncherComplete)
            LaunchedEffect(Unit) {
                delay(SplashWaitTime)
                currentOnTimeout(viewState.launchDestination)
            }
            Image(
                painter = painterResource(R.drawable.splash_bg),
                contentDescription = "",
                modifier = modifier.matchParentSize()
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GradientText(mutableStateOf("X").value, colors = whiteGhostGradient)
            }
        }
    }
}