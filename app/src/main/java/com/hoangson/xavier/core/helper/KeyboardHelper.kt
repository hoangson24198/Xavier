package com.hoangson.xavier.core.helper

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object KeyboardHelper {
    fun hideKeyboardIfNeed(activity: Activity?) {
        if (activity == null) return
        try {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val v = activity.currentFocus
            if (v == null || inputMethodManager == null) return
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}