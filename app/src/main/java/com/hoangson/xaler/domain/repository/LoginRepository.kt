package com.hoangson.xaler.domain.repository

import com.hoangson.xaler.core.models.Output
import com.hoangson.xaler.domain.model.LoginEntity

interface LoginRepository {

     fun login(username: String, password: String) : Output<LoginEntity>
}