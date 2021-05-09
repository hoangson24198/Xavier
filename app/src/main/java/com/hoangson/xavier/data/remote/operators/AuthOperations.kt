package com.hoangson.xavier.data.remote.operators

import com.hoangson.xavier.core.bases.BaseResponse
import com.hoangson.xavier.data.models.User
import io.reactivex.Observable
import com.google.gson.JsonObject
import retrofit2.Response

interface AuthOperations {
    suspend fun login(username : String,password : String) : Response<BaseResponse<User>>

    suspend fun register(username : String,password : String, displayName : String,gender : Int) : Response<BaseResponse<User>>
}