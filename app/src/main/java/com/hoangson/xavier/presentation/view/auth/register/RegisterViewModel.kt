package com.hoangson.xavier.presentation.view.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.hoangson.xavier.core.models.Command
import com.hoangson.xavier.domain.remote.auth.RegisterUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val usecase : RegisterUserCase
) : ViewModel() {
    private val _registerResultLiveData = MutableLiveData<Command?>()
    val registerResultLiveData: LiveData<Command?>
        get() = _registerResultLiveData

    fun register(username : String,
    password : String,
    displayName : String,
    gender : Int){
        viewModelScope.launch {
            val request = JsonObject()
            request.addProperty("username",username)
            request.addProperty("password",password)
            request.addProperty("display_name",displayName)
            request.addProperty("gender",gender)


        }
    }
}