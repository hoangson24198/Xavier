package com.hoangson.xavier.presentation.view.splash

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hoangson.xavier.core.bases.BaseActivity
import com.hoangson.xavier.core.util.checkAllMatched
import com.hoangson.xavier.presentation.view.auth.launchAuthActivity
import com.hoangson.xavier.presentation.view.main.launchMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
    }
    @Preview
    @Composable
    override fun setContent() {
        super.setContent()
        SplashView(
            onLauncherComplete = { destination ->
                when (destination) {
                    LaunchDestination.MAIN_ACTIVITY -> launchMainActivity(context = this)
                    LaunchDestination.AUTH_ACTIVITY -> launchAuthActivity(context = this)
                }.checkAllMatched
                finish()
            }
        )
    }
}