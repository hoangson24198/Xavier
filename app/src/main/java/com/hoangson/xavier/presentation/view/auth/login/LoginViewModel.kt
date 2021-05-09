package com.hoangson.xavier.presentation.view.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.hoangson.xavier.core.helper.HandleState
import com.hoangson.xavier.core.models.Command
import com.hoangson.xavier.data.models.User
import com.hoangson.xavier.domain.pref.OnUserLoggedInSuspenUseCase
import com.hoangson.xavier.domain.remote.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val usecase : LoginUseCase,
    val userLoggedInSuspenUseCase: OnUserLoggedInSuspenUseCase
) : ViewModel() {
    private val _loginResultLiveData = MutableLiveData<Long>()

    private val _userNameErrorLiveData = MutableLiveData<String?>()
    private val _passwordErrorLiveData = MutableLiveData<String?>()

    val userNameErrorLiveData: LiveData<String?>
        get() = _userNameErrorLiveData

    val passwordErrorLiveData: LiveData<String?>
        get() = _passwordErrorLiveData

    val loginResultLiveData: LiveData<Long>
        get() = _loginResultLiveData

    fun login(
        username : String,
        password : String
    ){
        viewModelScope.launch{
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
            val users = HandleState(viewModelScope.coroutineContext + Dispatchers.IO){
                usecase(request)
            }

            /*usecase.login(username, password).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    command.value = Command.Loading
                }
                .doFinally {
                    command.value = Command.NoLoading
                }
                .subscribe ({
                    if (it.isSuccess()){
                        command.value = Command.Success
                        val user = it.Object
                        it.Object?.let { it1 -> usecase.writeToLocal(it1) }
                    }else
                        command.value = Command.Error(it.message)
                },{
                    command.value = it.message?.let { it1 -> Command.Error(it1) }
                })*/
        }
    }
}