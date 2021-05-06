package com.hoangson.xavier.presentation.view.splash

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.core.util.navigateSafely
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @Composable
    override fun setContent() {
        super.setContent()
        SplashView(
            onLauncherComplete = { destination ->
                lifecycleScope.launch {
                    when (destination) {
                        LaunchDestination.MAIN_ACTIVITY -> {
                            navController.navigateSafely(
                                SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                            )
                        }
                        LaunchDestination.AUTH_ACTIVITY ->{
                            navController.navigateSafely(SplashFragmentDirections.actionSplashFragmentToAuthFragment())
                        }
                    }
                }
            }
        )
    }
}