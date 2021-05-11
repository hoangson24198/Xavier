package com.hoangson.xavier.data.remote

import com.google.gson.JsonObject
import com.hoangson.xavier.core.bases.BaseResponse
import com.hoangson.xavier.core.helper.handleApi
import com.hoangson.xavier.data.models.User
import com.hoangson.xavier.data.remote.operators.AuthOperations
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: ApiService
) : AuthOperations {
    override suspend fun login(user : JsonObject): Response<BaseResponse<User>> {
        return service.login(user)
    }

    override suspend fun register(
        user : JsonObject
    ): Response<BaseResponse<User>> {
        return service.register(user)
    }

}