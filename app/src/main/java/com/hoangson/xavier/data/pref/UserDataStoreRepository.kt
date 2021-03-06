package com.hoangson.xavier.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.data.pref.operators.UserDataStoreOperations
import com.hoangson.xavier.domain.pref.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreRepository @Inject constructor(
    private val dataStore : DataStore<Preferences>
) : UserDataStoreOperations{
    override suspend fun setLoggedInUser(username : String, userId : Long) {
        dataStore.edit {
            it[PreferencesKeys.userName] = username
            it[PreferencesKeys.userId] = userId
        }
    }

    override suspend fun clearUserData(){
        dataStore.edit {
            it.clear()
        }
    }

    override fun readUserId() : Flow<Result<Long>>{
        return dataStore.data
            .map {
            Result.Success(it[PreferencesKeys.userId] ?: -1)
        }
            .catch {
                Result.Error(Exception(it))
            }
    }

    override fun readUserName() : Flow<Result<String>>{
        return dataStore.data
            .map {
                Result.Success(it[PreferencesKeys.userName] ?: "")
            }
            .catch {
                Result.Error(Exception(it))
            }
    }

    override fun loggedInUserIdLiveData(): LiveData<Long?> = dataStore.data.map{ it[PreferencesKeys.userId]}.asLiveData()
}