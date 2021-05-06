package com.hoangson.xavier.presentation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.domain.pref.OnUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onUserLoggedInUseCase: OnUserLoggedInUseCase
) : ViewModel() {
    private val _loggedInLiveData = MutableLiveData<Long>()
    val loggedInLiveData: LiveData<Long>
        get() = _loggedInLiveData

    fun verifyLogin() {
        viewModelScope.launch {
            onUserLoggedInUseCase(Unit).collect { result ->
                if (result is Result.Success) {
                    if (result.data != -1L) {
                        result.data.let {
                            _loggedInLiveData.postValue(it)
                        }
                    }
                }
            }
        }
    }
}