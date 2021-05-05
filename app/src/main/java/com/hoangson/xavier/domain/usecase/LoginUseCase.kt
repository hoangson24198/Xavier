package com.hoangson.xavier.domain.usecase

import com.hoangson.xavier.core.bases.BaseUseCase
import com.hoangson.xavier.core.models.Output
import com.hoangson.xavier.domain.model.LoginEntity
import com.hoangson.xavier.domain.repository.LoginRepository

class LoginUseCase(var loginRepository: LoginRepository) : BaseUseCase<LoginEntity, LoginUseCase.Params>() {

    override suspend fun run(params: Params): Output<LoginEntity> {

        return loginRepository.login(params.username, params.password)
    }

    data class Params(val username: String, val password: String)

}