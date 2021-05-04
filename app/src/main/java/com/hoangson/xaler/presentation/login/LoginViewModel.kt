package com.hoangson.xaler.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangson.xaler.R
import com.hoangson.xaler.core.util.DataValidator
import com.hoangson.xaler.core.bases.BaseViewModel
import com.hoangson.xaler.core.models.Output
import com.hoangson.xaler.domain.model.LoginEntity
import com.hoangson.xaler.domain.usecase.LoginUseCase

class LoginViewModel(var loginUseCase: LoginUseCase) : BaseViewModel(loginUseCase) {

    private val _loginForm = MutableLiveData<Output<LoginFormState>>()

    val loginFormState: LiveData<Output<LoginFormState>> = _loginForm

    private val _login = MutableLiveData<Output<LoginEntity>>()

    val login: LiveData<Output<LoginEntity>> = _login

    fun login(username: String, password: String) {

        loginUseCase(LoginUseCase.Params(username, password)) {

            _login.value = it
        }
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = Output.Success(
                LoginFormState(usernameError = R.string.invalid_username)
            )
        } else if (!isPasswordValid(password)) {
            _loginForm.value = Output.Success(
                LoginFormState(passwordError = R.string.invalid_password)
            )
        } else {
            _loginForm.value = Output.Success(LoginFormState(isDataValid = true))
        }
    }


    private fun isUserNameValid(username: String): Boolean = DataValidator.isUsernameValid(username)

    private fun isPasswordValid(password: String): Boolean = DataValidator.iPasswordValid(password)

    override fun onCleared() {

        super.onCleared()

        loginUseCase.onCleared()
    }
}