package com.hoangson.xavier.core.helper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.google.gson.JsonSyntaxException
import com.hoangson.xavier.core.models.ActionCommand
import com.hoangson.xavier.core.models.Result
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
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
                    emit(Result.Loading)
                }

                ActionCommand.SwipeRefresh -> {
                    emit(Result.SwipeRefreshing)
                }

                ActionCommand.Retry -> {
                    emit(Result.Retrying)
                }
                ActionCommand.LoadDone -> {
                    emit(Result.NoLoading)
                }
            }

            try {
                val response = fetchData()
                val body = response.body()
                when {
                    response.isSuccessful && body != null -> {
                        data = body
                        emit(Result.Success(body))
                    }
                    action.value == ActionCommand.SwipeRefresh -> {
                        emit(Result.SwipeRefreshFailure(Exception()))
                    }
                    else -> {
                        emit(Result.Error(Exception()))
                    }
                }
            } catch (exception: Exception) {
                when {
                    action.value == ActionCommand.SwipeRefresh -> {
                        emit(Result.SwipeRefreshFailure(Exception()))
                        data?.let {
                            // emit success with existing data
                            emit(Result.Success(it))
                        }
                    }
                    else -> {
                        emit(Result.Error(Exception()))
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
var isConnectedToNetwork = true

suspend fun <T : Any> handleApi(
    call: suspend () -> Response<T>,
    errorMessage: String = "Problem Fetching data at the moment!"
): Result<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            isConnectedToNetwork = true
            response.body()?.let {
                return Result.Success(it)
            }
        }
        response.errorBody()?.let {
            return try {
                val errorString = it.string()
                val errorObject = JSONObject(errorString)
                Result.Failure(errorObject.getString("status_message") ?: errorMessage)
            } catch (ignored: JsonSyntaxException) {
                Result.Failure(ignored.message.toString())
            }
        }
        return Result.Failure(errorMessage)
    } catch (e: Exception) {
        if (e is IOException) {
            isConnectedToNetwork = false
        }
        return Result.Failure(e.message ?: errorMessage)
    }
}