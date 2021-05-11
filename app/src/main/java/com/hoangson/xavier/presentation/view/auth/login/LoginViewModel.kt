package com.hoangson.xavier.presentation.view.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.hoangson.xavier.core.helper.handleApi
import com.hoangson.xavier.core.models.Command
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.domain.pref.OnUserLoggedInSuspenUseCase
import com.hoangson.xavier.domain.remote.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val usecase : LoginUseCase,
    val userLoggedInSuspenUseCase: OnUserLoggedInSuspenUseCase
) : ViewModel() {
    private val _loginResultLiveData = MutableLiveData<Command?>()

    private val _userNameErrorLiveData = MutableLiveData<String?>()
    private val _passwordErrorLiveData = MutableLiveData<String?>()

    val userNameErrorLiveData: LiveData<String?>
        get() = _userNameErrorLiveData

    val passwordErrorLiveData: LiveData<String?>
        get() = _passwordErrorLiveData

    val loginResultLiveData: LiveData<Command?>
        get() = _loginResultLiveData

    fun login(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            Timber.d("HsLogin ${username} and ${password}")
            _userNameErrorLiveData.postValue(null)
            _passwordErrorLiveData.postValue(null)
            if (username.isEmpty()) {
                _userNameErrorLiveData.postValue("Username cannot be empty!")
                return@launch
            }
            if (password.isEmpty()) {
                _passwordErrorLiveData.postValue("Password cannot be empty!")
                return@launch
            }
            val request = JsonObject()
            request.addProperty("username",username)
            request.addProperty("password",password)
            val login = handleApi({usecase(request)})
            when(login){
                is Result.Success -> {
                    val data = login.data
                    if (data.isSuccess()){
                        /*data.Object?.let {
                            userLoggedInSuspenUseCase(it)
                        }*/
                        _loginResultLiveData.value = Command.Success
                        _loginResultLiveData.value = Command.NoLoading
                    }
                }
                is Result.Failure -> {
                    _loginResultLiveData.value = Command.Error
                    _loginResultLiveData.value = Command.NoLoading
                }
            }
        }
    }
}