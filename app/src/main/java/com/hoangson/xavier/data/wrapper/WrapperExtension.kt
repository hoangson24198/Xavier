package com.hoangson.xavier.data.wrapper

import com.hoangson.xavier.data.remote.LoginResponse
import com.hoangson.xavier.domain.model.LoginEntity


fun LoginResponse.toLoginResponse() = LoginEntity(isLoggedIn = loginSuccess, message = rMessage )


