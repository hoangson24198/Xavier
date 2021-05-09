package com.hoangson.xavier.domain.pref

import com.hoangson.xavier.core.di.IoDispatcher
import com.hoangson.xavier.data.models.User
import com.hoangson.xavier.data.pref.DataStoreRepository
import com.hoangson.xavier.data.pref.UserDataStoreRepository
import com.hoangson.xavier.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class OnUserLoggedInSuspenUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendUseCase<User,Unit> (dispatcher){
    override suspend fun execute(parameters: User) {
        userDataStoreRepository.setLoggedInUser(userId = parameters.id,username = parameters.username)
    }
}