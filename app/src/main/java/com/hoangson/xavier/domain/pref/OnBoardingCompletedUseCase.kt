package com.hoangson.xavier.domain.pref

import com.hoangson.xavier.core.di.IoDispatcher
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.data.pref.DataStoreOperations
import com.hoangson.xavier.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingCompletedUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreOperations,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Boolean>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<Boolean>> =
        dataStoreRepository.read(PreferencesKeys.onBoardingCompletedKey)
}