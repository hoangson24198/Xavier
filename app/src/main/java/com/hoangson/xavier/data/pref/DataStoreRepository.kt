package com.hoangson.xavier.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.hoangson.xavier.core.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository<T,D> @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperations<T,D> {

    override suspend fun save(key: Preferences.Key<T>, value: D) {
        dataStore.edit {
            it[key] = value
        }
    }

    override fun read(key: Preferences.Key<T>): Flow<Result<D>> {
        return dataStore.data
            .map {
                Result.Success(it[key])
            }.catch {
                Result.Error(Exception(it))
            }
    }
}