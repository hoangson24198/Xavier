package com.hoangson.xavier.domain

import com.hoangson.xavier.core.models.Command
import com.hoangson.xavier.core.models.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class SuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): Command<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    Command.Success(it)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            Command.Failure(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
