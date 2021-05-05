package com.hoangson.xavier.domain.repository

import com.hoangson.xavier.core.models.Output
import com.hoangson.xavier.domain.model.LoginEntity

interface LoginRepository {

     fun login(username: String, password: String) : Output<LoginEntity>
}