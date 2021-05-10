package com.hoangson.xavier.data.remote

import com.hoangson.xavier.core.bases.BaseResponse
import com.hoangson.xavier.data.models.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("user/login")
    suspend fun login(@Body user : JsonObject) : Response<BaseResponse<User>>

    @POST("user/register")
    suspend fun register(@Body user : JsonObject) : Response<BaseResponse<User>>

}