package com.hoangson.xavier.core.helper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.hoangson.xavier.core.di.IoDispatcher
import com.hoangson.xavier.core.models.ActionCommand
import com.hoangson.xavier.core.models.Command
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class HandleState<T>(
    private val coroutineContext: CoroutineContext,
    fetchData: suspend () -> Response<T>
) {
    private val action = MutableLiveData<ActionCommand>()
    private var data: T? = null // backing data

    val state = action.switchMap {
        liveData(context = coroutineContext) {
            when (action.value) {
                ActionCommand.Load -> {
                    emit(Command.Loading)
                }

                ActionCommand.SwipeRefresh -> {
                    emit(Command.SwipeRefreshing)
                }

                ActionCommand.Retry -> {
                    emit(Command.Retrying)
                }
                ActionCommand.LoadDone -> {
                    emit(Command.NoLoading)
                }
            }

            try {
                val response = fetchData()
                val body = response.body()
                when {
                    response.isSuccessful && body != null -> {
                        data = body
                        emit(Command.Success(body))
                    }
                    action.value == ActionCommand.SwipeRefresh -> {
                        emit(Command.SwipeRefreshFailure(Exception()))
                    }
                    else -> {
                        emit(Command.Failure(Exception()))
                    }
                }
            } catch (exception: Exception) {
                when {
                    action.value == ActionCommand.SwipeRefresh -> {
                        emit(Command.SwipeRefreshFailure(Exception()))
                        data?.let {
                            // emit success with existing data
                            emit(Command.Success(it))
                        }
                    }
                    else -> {
                        emit(Command.Failure(Exception()))
                    }
                }
            }
        }
    }

    // Helpers for triggering different actions

    fun retry() {
        action.value = ActionCommand.Retry
    }

    fun swipeRefresh() {
        action.value = ActionCommand.SwipeRefresh
    }

    fun load() {
        action.value = ActionCommand.Load
    }

    fun loadDone() {
        action.value = ActionCommand.LoadDone
    }
}