package com.hoangson.xavier.presentation.view.splash

import android.os.Bundle
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.core.util.checkAllMatched
import com.hoangson.xavier.core.util.navigateSafely
import com.hoangson.xavier.presentation.compose.layout.GradientText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SplashWaitTime: Long = 2000
@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.hide()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
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
}