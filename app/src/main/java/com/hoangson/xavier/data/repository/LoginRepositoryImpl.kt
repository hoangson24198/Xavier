package com.hoangson.xavier.data.repository

import com.hoangson.xavier.core.bases.BaseRepository
import com.hoangson.xavier.core.models.Output
import com.hoangson.xavier.data.remote.LoginResponse
import com.hoangson.xavier.data.remote.RemoteDataSource
import com.hoangson.xavier.data.wrapper.toLoginResponse
import com.hoangson.xavier.domain.model.LoginEntity
import com.hoangson.xavier.domain.repository.LoginRepository

class LoginRepositoryImpl : BaseRepository(), LoginRepository {

    override  fun login(username: String, password: String): Output<LoginEntity> {

        return safeApiCall(remoteDataSource.makeRemoteCall(RemoteDataSource::class.java)
            .login(username, password), transform = { it.toLoginResponse() }, default = LoginResponse(), error = "")

    }


}

