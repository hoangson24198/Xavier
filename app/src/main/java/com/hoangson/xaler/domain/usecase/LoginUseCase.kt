package com.hoangson.xaler.domain.usecase

import com.hoangson.xaler.core.bases.BaseUseCase
import com.hoangson.xaler.core.models.Output
import com.hoangson.xaler.domain.model.LoginEntity
import com.hoangson.xaler.domain.repository.LoginRepository

class LoginUseCase(var loginRepository: LoginRepository) : BaseUseCase<LoginEntity, LoginUseCase.Params>() {

    override suspend fun run(params: Params): Output<LoginEntity> {

        return loginRepository.login(params.username, params.password)
    }

    data class Params(val username: String, val password: String)

}