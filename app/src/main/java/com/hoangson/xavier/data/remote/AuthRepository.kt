package com.hoangson.xavier.data.remote

import com.google.gson.JsonObject
import com.hoangson.xavier.core.bases.BaseResponse
import com.hoangson.xavier.core.helper.HandleState
import com.hoangson.xavier.data.models.User
import com.hoangson.xavier.data.pref.UserDataStoreRepository
import com.hoangson.xavier.data.remote.operators.AuthOperations
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: ApiService
) : AuthOperations {
    override suspend fun login(username: String, password: String): Response<BaseResponse<User>> {
        val request = JsonObject()
        request.addProperty("username", username)
        request.addProperty("password", password)
        return service.login(request)
    }

    override suspend fun register(
        username: String,
        password: String,
        displayName: String,
        gender: Int
    ): Response<BaseResponse<User>> {
        val request = JsonObject()
        request.addProperty("username",username)
        request.addProperty("password",password)
        request.addProperty("display_name",displayName)
        request.addProperty("gender",gender)
        return service.register(request)
    }

}