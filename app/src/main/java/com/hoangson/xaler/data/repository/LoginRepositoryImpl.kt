package com.hoangson.xaler.data.repository

import com.hoangson.xaler.core.bases.BaseRepository
import com.hoangson.xaler.core.models.Output
import com.hoangson.xaler.data.remote.LoginResponse
import com.hoangson.xaler.data.remote.RemoteDataSource
import com.hoangson.xaler.data.wrapper.toLoginResponse
import com.hoangson.xaler.domain.model.LoginEntity
import com.hoangson.xaler.domain.repository.LoginRepository

class LoginRepositoryImpl : BaseRepository(), LoginRepository {

    override  fun login(username: String, password: String): Output<LoginEntity> {

        return safeApiCall(remoteDataSource.makeRemoteCall(RemoteDataSource::class.java)
            .login(username, password), transform = { it.toLoginResponse() }, default = LoginResponse(), error = "")

    }


}

