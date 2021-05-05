package com.hoangson.xavier.domain.model


class LoginEntity(var isLoggedIn: Boolean = false, var message: String = "Login is Invalid") {


    companion object {
        fun loggedIn() = LoginEntity(false)
    }
}

