package com.hoangson.xavier.presentation.view.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangson.xavier.R
import com.hoangson.xavier.data.models.OnBoardItem
import com.hoangson.xavier.domain.pref.OnBoardingCompleteSaveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val onBoardingCompleteSaveUseCase: OnBoardingCompleteSaveUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(false)
    val viewState: StateFlow<Boolean>
        get() = _state

    fun getStartedClick() {
        viewModelScope.launch {
            onBoardingCompleteSaveUseCase(true)
            _state.value = true
        }
    }

    fun getOnBoardingItemsList() = OnBoardingProvider.onBoardingDataList
}
object OnBoardingProvider {
    val onBoardingDataList = listOf(
        OnBoardItem(
            R.string.onBoarding_page_1_title,
            R.string.onBoarding_page_1_Description,
        ),
        OnBoardItem(
            R.string.onBoarding_page_2_title,
            R.string.onBoarding_page_2_Description,
        ),
        OnBoardItem(
            R.string.onBoarding_page_3_title,
            R.string.onBoarding_page_3_Description,
        )
    )
}