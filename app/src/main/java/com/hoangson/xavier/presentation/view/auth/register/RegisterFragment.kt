package com.hoangson.xavier.presentation.view.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.presentation.compose.layout.CircleWave
import com.hoangson.xavier.presentation.compose.layout.GradientText
import com.hoangson.xavier.presentation.compose.layout.LoadingContent
import com.hoangson.xavier.presentation.compose.layout.RingOfCircles
import com.hoangson.xavier.presentation.ui.whiteGhostGradient

class RegisterFragment : BaseFragment() {

    @Preview
    @Composable
    override fun setContent() {
        super.setContent()
        ConstraintLayout(Modifier.fillMaxSize()) {
            LoadingContent(true)
            /*Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircleWave(modifier = Modifier.fillMaxSize())
                //GradientText(mutableStateOf("X").value, colors = whiteGhostGradient)
            }*/
        }
    }
}