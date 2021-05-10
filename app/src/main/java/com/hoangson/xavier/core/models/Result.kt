package com.hoangson.xavier.core.models
sealed class Result<out R> {
    object Loading : Result<Nothing>()
    object NoLoading : Result<Nothing>()
    object Retrying : Result<Nothing>()
    object SwipeRefreshing : Result<Nothing>()
    data class Success<out D>(val data: D) : Result<D>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Failure(val message: String) : Result<Nothing>()
    data class SwipeRefreshFailure(val exception: java.lang.Exception) : Result<Nothing>()
}

val <D> Result<D>.data: D?
    get() = (this as? Result.Success)?.data

