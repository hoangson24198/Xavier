package com.hoangson.xavier.domain.pref

import com.hoangson.xavier.core.di.IoDispatcher
import com.hoangson.xavier.core.models.Result
import com.hoangson.xavier.data.pref.UserDataStoreRepository
import com.hoangson.xavier.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnUserLoggedInUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit,Long>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<Long>> =
        userDataStoreRepository.readUserId()

}