package com.hoangson.xaler.core.bases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hoangson.xaler.core.util.LayoutResId

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutResId: Int = getLayout()
        if (layoutResId != LayoutResId.LAYOUT_NOT_DEFINED) {
            setContentView(layoutResId)

            onSyncViews(savedInstanceState)
            onSyncEvents()
            onSyncData()
        }
    }

    protected open fun getLayout(): Int {
        return if (javaClass.getAnnotation(LayoutResId::class.java) != null) javaClass.getAnnotation(
            LayoutResId::class.java
        )!!.layout else LayoutResId.LAYOUT_NOT_DEFINED
    }

    protected abstract fun onSyncViews(savedInstanceState: Bundle?)

    protected abstract fun onSyncEvents()

    protected abstract fun onSyncData()

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

    protected open fun getCurrentFragment(): Fragment? {
        if (getFragmentContainer() != LayoutResId.LAYOUT_NOT_DEFINED) {
            val fragment =
                supportFragmentManager.findFragmentById(getFragmentContainer())
            if (fragment != null && fragment.isVisible) return fragment
        }
        return null
    }
}