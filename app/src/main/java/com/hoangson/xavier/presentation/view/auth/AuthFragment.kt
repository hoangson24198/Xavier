package com.hoangson.xavier.presentation.view.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.compose.animation.core.Transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hoangson.xavier.R
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.core.util.navigateSafely
import com.hoangson.xavier.presentation.compose.animation.*
import com.hoangson.xavier.presentation.compose.layout.*
import com.hoangson.xavier.presentation.ui.whiteGhostGradient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment() {
    private var canAnimate = true
    private var isOnBoarding: Boolean = true

    override fun onArgumentsReady(bundle: Bundle) {
        isOnBoarding = AuthFragmentArgs.fromBundle(bundle).isOnBoard
        canAnimate = isOnBoarding && canAnimate
    }

    @Composable
    override fun setContent() {
        super.setContent()
        Box(
            modifier = Modifier
                .fillMaxSize().background(MaterialTheme.colors.background)
        ) {
            if (!isOnBoarding) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    modifier = Modifier.align(Alignment.TopStart)
                        .size(56.dp)
                        .clickable {
                            mainActivity.onBackPressed()
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                    contentScale = ContentScale.Inside,
                    contentDescription = null
                )
            }
            VideoBackGround("asset:///sunset.mp4")
            var mainState by remember { mutableStateOf(MainTransitionState.START)}
            Handler(Looper.getMainLooper()).postDelayed({
                mainState = MainTransitionState.END
            }, 10)
            Column(
                modifier = Modifier.fillMaxSize().offset(y = titleOffsetPropkey(mainState,value = -150)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GradientText("XAVIER".substring(0, titleIndexPropkey(mainState)),colors = whiteGhostGradient)
            }
            Column(
                Modifier.fillMaxWidth().align(Alignment.Center).offset(y = 50.dp)
                    .alpha(titleAlphaPropkey(mainState))
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Body(text = "Almost There!")
                columnSpacer(value = 10)
                CenteredCaption(text = "Login/Register to access your watchlist")
                columnSpacer(value = 20)
                LoginButtonGroup()
                columnSpacer(value = 30)
                CenteredCaption(text = "First page in Compose project")
            }
        }
    }

    @Composable
    fun LoginButtonGroup() {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CardButton(text = "Login", icon = R.drawable.ic_baseline_login_24,
                modifier = Modifier.weight(1F),
                onClick = {
                    navController.navigateSafely(AuthFragmentDirections.actionAuthFragmentToLoginFragment())
                })
            rowSpacer(value = 16)
            CardButton(
                text = "Register",
                icon = R.drawable.ic_baseline_how_to_reg_24,
                modifier = Modifier.weight(1F),
                onClick = {
                    navController.navigateSafely(AuthFragmentDirections.actionAuthFragmentToRegisterFragment())
                })
        }
    }
}