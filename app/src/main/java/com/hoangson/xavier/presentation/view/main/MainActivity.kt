package com.hoangson.xavier.presentation.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.hoangson.xavier.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel: MainViewModel by viewModels()

    var loggedInUserId: Long = -1L
        set(value) {
            field = value
            loginState.value = value != -1L
        }

    private val isLoggedIn
        get() = loggedInUserId != -1L

    val loginState = mutableStateOf(isLoggedIn)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewTreeLifecycleOwner.set(window.decorView,this)

        mainActivityViewModel.loggedInLiveData.observe(this) {
            loggedInUserId = it
        }
        mainActivityViewModel.verifyLogin()
    }
}