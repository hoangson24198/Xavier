package com.hoangson.xavier.data.remote.operators

import com.google.gson.JsonObject
import com.hoangson.xavier.core.bases.BaseResponse
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.data.models.User
import retrofit2.Response

interface AuthOperations {
    suspend fun login(user : JsonObject) : Response<BaseResponse<User>>

    suspend fun register(user : JsonObject) : Response<BaseResponse<User>>
}