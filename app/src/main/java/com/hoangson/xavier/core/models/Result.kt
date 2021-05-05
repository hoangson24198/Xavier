package com.hoangson.xavier.core.models
sealed class Result<out R> {
    data class Success<out D>(val data: D) : Result<D>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

val <D> Result<D>.data: D?
    get() = (this as? Result.Success)?.data

