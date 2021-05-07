package com.hoangson.xavier.domain.pref

import com.hoangson.xavier.core.di.IoDispatcher
import com.hoangson.xavier.data.pref.DataStoreRepository
import com.hoangson.xavier.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class OnBoardingCompleteSaveUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Boolean, Unit>(dispatcher) {
    override suspend fun execute(parameters: Boolean) {
        dataStoreRepository.save(PreferencesKeys.onBoardingCompletedKey, true)
    }
}