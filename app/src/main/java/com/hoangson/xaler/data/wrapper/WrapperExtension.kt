package com.hoangson.xaler.data.wrapper

import com.hoangson.xaler.data.remote.LoginResponse
import com.hoangson.xaler.domain.model.LoginEntity


fun LoginResponse.toLoginResponse() = LoginEntity(isLoggedIn = loginSuccess, message = rMessage )


