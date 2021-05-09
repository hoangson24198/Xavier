package com.hoangson.xavier.data.pref.operators

import androidx.lifecycle.LiveData
import com.hoangson.xavier.core.models.Result
import kotlinx.coroutines.flow.Flow

interface UserDataStoreOperations {
    suspend fun setLoggedInUser(username : String, userId : Long)

    suspend fun clearUserData()

    fun readUserId() : Flow<Result<Long>>

    fun readUserName() : Flow<Result<String>>

    fun loggedInUserIdLiveData(): LiveData<Long?>
}