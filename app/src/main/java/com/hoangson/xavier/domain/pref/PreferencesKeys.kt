package com.hoangson.xavier.domain.pref

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val onBoardingCompletedKey = booleanPreferencesKey("show_completed")
    val userId = longPreferencesKey("user_id")
    val userName = stringPreferencesKey("user_name")
}