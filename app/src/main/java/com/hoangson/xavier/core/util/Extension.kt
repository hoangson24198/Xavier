package com.hoangson.xavier.core.util


import androidx.navigation.NavController
import androidx.navigation.NavDirections
import timber.log.Timber

val <T> T.checkAllMatched: T
    get() = this

fun NavController.navigateSafely(directions: NavDirections) {
    try {
        navigate(directions)
    } catch (e: Exception) {
        Timber.d("NavigationError ${e.printStackTrace()}")
    }
}
