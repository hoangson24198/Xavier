package com.hoangson.xavier.presentation.view.auth

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import com.hoangson.xavier.core.bases.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

fun launchAuthActivity(context: Context) {
    context.startActivity(Intent(context, AuthActivity::class.java))
}

@AndroidEntryPoint
class AuthActivity : BaseActivity() {
    @Composable
    override fun setContent() {
        super.setContent()

    }
}