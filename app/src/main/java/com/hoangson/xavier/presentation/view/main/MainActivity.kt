package com.hoangson.xavier.presentation.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.hoangson.xavier.R
import dagger.hilt.android.AndroidEntryPoint

fun launchMainActivity(context: Context) {
    context.startActivity(Intent(context, MainActivity::class.java))
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewTreeLifecycleOwner.set(window.decorView, this)
    }
}