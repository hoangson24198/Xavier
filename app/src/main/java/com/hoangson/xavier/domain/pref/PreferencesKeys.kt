package com.hoangson.xavier.domain.pref

import androidx.datastore.preferences.core.booleanPreferencesKey
object PreferencesKeys {
    val onBoardingCompletedKey = booleanPreferencesKey("show_completed")
}