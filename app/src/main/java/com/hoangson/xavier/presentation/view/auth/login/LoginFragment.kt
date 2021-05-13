package com.hoangson.xavier.presentation.view.auth.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.hoangson.xavier.R
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.core.models.Command
import com.hoangson.xavier.core.util.navigateSafely
import com.hoangson.xavier.presentation.compose.animation.MainTransitionState
import com.hoangson.xavier.presentation.compose.animation.titleAlphaPropkey
import com.hoangson.xavier.presentation.compose.animation.titleIndexPropkey
import com.hoangson.xavier.presentation.compose.animation.titleOffsetPropkey
import com.hoangson.xavier.presentation.compose.layout.*
import com.hoangson.xavier.presentation.ui.whiteGhost
import com.hoangson.xavier.presentation.ui.whiteGhostGradient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private var canAnimate = true
    private var isOnBoarding: Boolean = true

    override fun onArgumentsReady(bundle: Bundle) {
        isOnBoarding = LoginFragmentArgs.fromBundle(bundle).isOnBoard
        canAnimate = isOnBoarding && canAnimate
    }

    @Composable
    override fun setContent() {
        LoginView(
            onLoginComplete = {
                navController.navigateSafely(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        )

    }

    @Composable
    fun LoginView(
        onLoginComplete: () -> Unit
    ) {
        val stateLoading = remember { mutableStateOf(false) }
        val viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        ConstraintLayout(Modifier.fillMaxSize()) {
            val (back_button, column_logo, column_text) = createRefs()

            if (!isOnBoarding) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    modifier = Modifier.constrainAs(back_button) {
                        top.linkTo(parent.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 15.dp)
                    }.size(56.dp)
                        .clickable { mainActivity.onBackPressed() },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                    contentScale = ContentScale.Inside,
                    contentDescription = null
                )
            }
            VideoBackGround("asset:///sunset.mp4")
            var mainState by remember { mutableStateOf(MainTransitionState.START) }
            Handler(Looper.getMainLooper()).postDelayed({
                mainState = MainTransitionState.END
            }, 10)
            Column(
                modifier = Modifier.constrainAs(column_logo){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }.offset(y = titleOffsetPropkey(mainState, value = -250)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GradientText(
                    "XAVIER".substring(0, titleIndexPropkey(mainState)),
                    colors = whiteGhostGradient
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().constrainAs(column_text){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }.alpha(titleAlphaPropkey(mainState))
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    columnSpacer(value = 40)
                    H6(text = "Login", color = whiteGhost)
                    columnSpacer(value = 20)
                    CenteredCaption(
                        text = "By Logging in to Xavier, you will be able to access your events.",
                        Modifier.padding(20.dp, 0.dp)
                    )
                    columnSpacer(value = 30)
                }
                val userNameErrorState = viewModel.userNameErrorLiveData.observeAsState()
                val passwordErrorState = viewModel.passwordErrorLiveData.observeAsState()
                val username = remember { mutableStateOf(TextFieldValue("hoangson@gmail.com")) }

                OutlinedTextField(
                    value = username.value,
                    onValueChange = { text ->
                        username.value = text
                    },
                    label = { H6("Username", color = whiteGhost) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = userNameErrorState.value != null,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = whiteGhost
                    )
                )
                Text(
                    text = userNameErrorState.value ?: "",
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    modifier = Modifier.height(userNameErrorState.value.let { 20.dp } ?: 0.dp),
                    color = whiteGhost)
                columnSpacer(value = 16)

                val password = remember { mutableStateOf(TextFieldValue("123456")) }
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { text -> password.value = text },
                    label = { H6("Password", color = whiteGhost) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordErrorState.value != null,
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = whiteGhost)
                )
                Text(
                    text = passwordErrorState.value ?: "",
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    modifier = Modifier.height(passwordErrorState.value?.let { 20.dp } ?: 0.dp),
                    color = whiteGhost)
                columnSpacer(value = 30)

                CardButton(text = "Login", onClick = {
                    stateLoading.value = true
                    viewModel.login(username = username.value.text, password = password.value.text)
                })
                columnSpacer(value = 20)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    CenteredCaption(
                        text = "Forgot password?",
                        modifier = Modifier.padding(20.dp, 0.dp).clickable(onClick = {

                        })
                    )
                    columnSpacer(value = 20)
                    CenteredCaption(
                        text = "Don't have an account? Register now",
                        modifier = Modifier.padding(20.dp, 0.dp).clickable(onClick = {
                            navController.navigateSafely(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                        })
                    )
                }
            }
            LoadingContent(stateLoading.value)

        }
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Command.Loading -> {
                    stateLoading.value = true
                    Timber.d("HSLogin Loading")
                }
                is Command.NoLoading -> {
                    stateLoading.value = false
                    Timber.d("HSLogin No loading")
                }
                is Command.Success -> {
                    //onLoginComplete()
                }
                is Command.Error -> {
                    Timber.d("HSLogin failded")
                }
            }
        }
    }
}