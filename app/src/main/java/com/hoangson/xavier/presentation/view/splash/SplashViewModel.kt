package com.hoangson.xavier.presentation.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.core.models.data
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
    private val userLoggedInUseCase: OnUserLoggedInUseCase,
    private val onBoardingCompletedUseCase: OnBoardingCompletedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LauncherViewState())
    private val _viewState = MutableStateFlow(false)
    val state: StateFlow<LauncherViewState>
        get() = _state
    val viewState: StateFlow<Boolean>
        get() = _viewState

    init {
        getLaunchDestination()
    }

    private fun getLaunchDestination() {
        viewModelScope.launch {
            userLoggedInUseCase(Unit).collect { userId ->
                onBoardingCompletedUseCase(Unit).collect { isOnboardComplete ->
                    if (isOnboardComplete is Result.Success) {
                        _viewState.value = isOnboardComplete.data
                        if (!isOnboardComplete.data)
                            _state.value = LauncherViewState(LaunchDestination.ONBOARD)
                    } else {
                        if (userId is Result.Success) {
                            if (userId.data != -1L) {
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
    }
}

enum class LaunchDestination {
    AUTH_ACTIVITY,
    MAIN_ACTIVITY,
    ONBOARD
}

data class LauncherViewState(
    val launchDestination: LaunchDestination = LaunchDestination.AUTH_ACTIVITY,
)
