package com.hoangson.xavier.core.bases

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.hoangson.xavier.core.util.LayoutResId
import com.hoangson.xavier.presentation.ui.XavierTheme

abstract class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResId: Int = getLayout()
        if (layoutResId == LayoutResId.LAYOUT_NOT_DEFINED) {
            setContent {
                XavierTheme {
                    Surface(color = MaterialTheme.colors.surface) {
                        Box {
                            setContent()
                        }
                    }
                }
            }
        } else {
            setContentView(layoutResId)
        }
    }

    protected open fun getLayout(): Int {
        return if (javaClass.getAnnotation(LayoutResId::class.java) != null) javaClass.getAnnotation(
            LayoutResId::class.java
        )!!.layout else LayoutResId.LAYOUT_NOT_DEFINED
    }

    @Composable
    open fun setContent() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    protected open fun getFragmentContainer(): Int {
        return LayoutResId.LAYOUT_NOT_DEFINED
    }

    open fun fullScreen() {
        actionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    open fun exitFullScreen() {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /*protected open fun getCurrentFragment(): Fragment? {
        if (getFragmentContainer() != LayoutResId.LAYOUT_NOT_DEFINED) {
            val fragment = supportFragmentManager.findFragmentById(getFragmentContainer())
            if (fragment != null && fragment.isVisible) return fragment
        }
        return null
    }*/
}