package com.hoangson.xavier.presentation.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hoangson.xavier.core.bases.BaseFragment
import com.hoangson.xavier.presentation.compose.layout.GradientText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment() {

    @Composable
    override fun setContent() {
        super.setContent()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            GradientText(mutableStateOf("X").value)
        }
    }
}