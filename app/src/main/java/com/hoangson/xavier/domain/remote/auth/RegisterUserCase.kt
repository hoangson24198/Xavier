package com.hoangson.xavier.domain.remote.auth

import com.google.gson.JsonObject
import com.hoangson.xavier.core.bases.BaseResponse
import com.hoangson.xavier.data.models.User
import com.hoangson.xavier.data.remote.AuthRepository
import com.hoangson.xavier.domain.UseCase
import retrofit2.Response
import javax.inject.Inject

class RegisterUserCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<JsonObject, Response<BaseResponse<User>>>() {
    override suspend fun execute(parameters: JsonObject) : Response<BaseResponse<User>> {
        return authRepository.register(parameters)
    }

}