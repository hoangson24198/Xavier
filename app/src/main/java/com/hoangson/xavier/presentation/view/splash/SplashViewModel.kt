package com.hoangson.xavier.presentation.view.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.data.pref.DataStoreRepository
import com.hoangson.xavier.data.pref.UserDataStoreRepository
import com.hoangson.xavier.domain.pref.OnBoardingCompletedUseCase
import com.hoangson.xavier.domain.pref.OnUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val onDataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LauncherViewState())
    val state: StateFlow<LauncherViewState>
        get() = _state

    init {
        getLaunchDestination()
    }

    private fun getLaunchDestination() {
        viewModelScope.launch {
            onDataStoreRepository(Unit).collect { result ->
                if (result is Result.Success) {
                    if (result.data != -1L) {
                        _state.value = LauncherViewState(LaunchDestination.MAIN_ACTIVITY)
                    } else {
                        LauncherViewState(LaunchDestination.AUTH_ACTIVITY)
                    }
                } else {
                    LauncherViewState(LaunchDestination.AUTH_ACTIVITY)
                }
            }
        }
    }
}

enum class LaunchDestination {
    AUTH_ACTIVITY,
    MAIN_ACTIVITY
}

data class LauncherViewState(
    val launchDestination: LaunchDestination = LaunchDestination.AUTH_ACTIVITY,
)
