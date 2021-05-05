package com.hoangson.xavier.core.bases

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

interface LifecycleBehaviour : LifecycleObserver {
    var lifecycle: Lifecycle?


    fun bind(lifecycle: Lifecycle?) {
        if (this@LifecycleBehaviour.lifecycle != null) {
            return
        }

        this@LifecycleBehaviour.lifecycle = lifecycle
        this@LifecycleBehaviour.lifecycle?.addObserver(this@LifecycleBehaviour)
    }

    fun unbindIfNeeded() {
        this@LifecycleBehaviour.lifecycle?.removeObserver(this@LifecycleBehaviour)
        this@LifecycleBehaviour.lifecycle = null
    }


    fun lifecycleOnCreate() {
        Log.i(this@LifecycleBehaviour::class.java.simpleName, "onCreate")
    }

    fun lifecycleOnStart() {
        Log.i(this@LifecycleBehaviour::class.java.simpleName, "onStart")
    }

    fun lifecycleOnResume() {
        Log.i(this@LifecycleBehaviour::class.java.simpleName, "onResume")
    }

    fun lifecycleOnPause() {
        Log.i(this@LifecycleBehaviour::class.java.simpleName, "onPause")
    }

    fun lifecycleOnStop() {
        Log.i(this@LifecycleBehaviour::class.java.simpleName, "onStop")
    }

    fun lifecycleOnDestroy() {
        this@LifecycleBehaviour.unbindIfNeeded()
        Log.i(this@LifecycleBehaviour::class.java.simpleName, "onDestroy")
    }
}