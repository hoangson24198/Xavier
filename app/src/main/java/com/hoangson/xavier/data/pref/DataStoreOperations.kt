package com.hoangson.xavier.data.pref

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import com.hoangson.xavier.core.models.Result

interface DataStoreOperations<T,D> {
    suspend fun save(key: Preferences.Key<T>, value: D)
    fun read(key: Preferences.Key<T>): Flow<Result<D>>
}