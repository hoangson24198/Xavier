package com.hoangson.xavier.data.pref

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import com.hoangson.xavier.core.models.Result

interface DataStoreOperations {
    suspend fun save(key: Preferences.Key<Boolean>, value: Boolean)
    fun read(key: Preferences.Key<Boolean>): Flow<Result<Boolean>>
}