package com.hoangson.xavier.core.models

import java.lang.Exception

sealed class Command<out R> {
    object Loading : Command<Nothing>()
    object NoLoading : Command<Nothing>()
    object Retrying : Command<Nothing>()
    object SwipeRefreshing : Command<Nothing>()
    data class Success<T>(val data: T) : Command<T>()
    data class Failure(val exception: Exception) : Command<Nothing>()
    data class SwipeRefreshFailure(val exception: Exception) : Command<Nothing>()
}
