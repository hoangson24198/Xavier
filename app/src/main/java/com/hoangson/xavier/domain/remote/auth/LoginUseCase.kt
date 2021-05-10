package com.hoangson.xavier.domain.remote.auth

import com.google.gson.JsonObject
import com.hoangson.xavier.core.bases.BaseResponse
import com.hoangson.xavier.core.di.IoDispatcher
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.data.models.User
import com.hoangson.xavier.data.remote.AuthRepository
import com.hoangson.xavier.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository
) : UseCase<JsonObject,Response<BaseResponse<User>>>() {
    override suspend fun execute(parameters: JsonObject) : Response<BaseResponse<User>> {
        return authRepository.login(parameters["username"].asString,parameters["password"].asString)
    }

}