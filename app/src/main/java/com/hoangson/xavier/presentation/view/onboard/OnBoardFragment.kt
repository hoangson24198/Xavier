package com.hoangson.xavier.presentation.view.onboard

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoangson.xavier.R
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.core.util.navigateSafely
import com.hoangson.xavier.presentation.compose.animation.MainTransitionState
import com.hoangson.xavier.presentation.compose.animation.titleAlphaPropkey
import com.hoangson.xavier.presentation.compose.animation.titleIndexPropkey
import com.hoangson.xavier.presentation.compose.animation.titleOffsetPropkey
import com.hoangson.xavier.presentation.compose.layout.GradientText
import com.hoangson.xavier.presentation.compose.layout.OnBoardingSlide
import com.hoangson.xavier.presentation.compose.layout.mutedVideoPlayer
import com.hoangson.xavier.presentation.custom.MutedVideoView
import com.hoangson.xavier.presentation.ui.whiteGhostGradient
import com.hoangson.xavier.presentation.view.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.temporal.TemporalAdjusters.next

const val currentPageAnimation = "currentPageAnimation"
const val rotateAnimation = "rotateAnimation"

@AndroidEntryPoint
@ExperimentalAnimationApi
class OnBoardFragment : BaseFragment() {

    @Composable
    override fun setContent() {
        super.setContent()
        OnBoardingContent()
    }

    @Composable
    private fun VideoBackGround() {
        Box(modifier = Modifier.fillMaxSize()) {
            mutedVideoPlayer("asset:///sea.mp4")
        }
    }

    @Composable
    private fun onBoardFooter(){

    }

    @Composable
    fun OnBoardingContent() {
        val viewModel = ViewModelProvider(requireActivity()).get(OnBoardViewModel::class.java)
        val onBoardingItemsList = viewModel.getOnBoardingItemsList()
        val launchDestination: Boolean by viewModel.viewState.collectAsState()

        if (launchDestination) {
            navController.navigateSafely(OnBoardFragmentDirections.actionOnBoardFragmentToAuthFragment())
        }

        val currentPage = remember { mutableStateOf(0) }
        val transition = updateTransition(targetState = currentPage, label = currentPageAnimation)
        val rotation by transition.animateFloat(
            {
                tween(durationMillis = 1000)
            },
            label = rotateAnimation
        ) {
            if (it.value == 0) 0f else it.value * 360f
        }

        val getStartedVisible = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            VideoBackGround()
            var mainState by remember { mutableStateOf(MainTransitionState.START) }
            Handler(Looper.getMainLooper()).postDelayed({
                mainState = MainTransitionState.END
            }, 10)
            //Logo
            Column(
                modifier = Modifier.fillMaxSize()
                    .offset(y = titleOffsetPropkey(mainState, value = -200)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GradientText(
                    "XAVIER".substring(0, titleIndexPropkey(mainState)),
                    colors = whiteGhostGradient
                )
            }
            //Footer
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .alpha(titleAlphaPropkey(mainState))){
                AnimatedVisibility(
                    visible = getStartedVisible.value,
                    enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(initialAlpha = 0.3f)
                ) {
                    Button(
                        onClick = {
                            viewModel.getStartedClick()
                        },
                        modifier = Modifier
                            .padding(vertical = 32.dp, horizontal = 120.dp),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = stringResource(id = R.string.onBoarding_start),
                            style = MaterialTheme.typography.body2,
                            color = Color.White
                        )
                    }
                }
                AnimatedVisibility(
                    visible = !getStartedVisible.value,
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = {
                                viewModel.getStartedClick()
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.skip),
                                color = Color.White,
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Row(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            onBoardingItemsList.forEachIndexed { index, _ ->
                                OnBoardingSlide(
                                    selected = index == currentPage.value,
                                    Color.White,
                                    Icons.Filled.Album
                                )
                            }
                        }
                        TextButton(
                            onClick = {
                                if (currentPage.value != onBoardingItemsList.size - 1) {
                                    currentPage.value = currentPage.value + 1
                                }
                                if (currentPage.value != onBoardingItemsList.size - 2) {
                                    getStartedVisible.value = true
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = stringResource(id = R.string.next),
                                color = Color.White,
                                style = MaterialTheme.typography.body2
                            )
                            Image(
                                imageVector = Icons.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
            }

            //Content
            Column(
                Modifier.fillMaxWidth().align(Alignment.Center).offset(y = 50.dp)
                    .alpha(titleAlphaPropkey(mainState))
                    .padding(0.dp, 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = stringResource(id = onBoardingItemsList[currentPage.value].titleId),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(
                        start = 30.dp, top = 16.dp, end = 30.dp
                    )
                )
                Text(
                    text = stringResource(
                        id = onBoardingItemsList[currentPage.value].descriptionId
                    ),
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 30.dp, top = 16.dp, end = 30.dp),
                    color = Color.LightGray
                )
            }
        }
    }
}