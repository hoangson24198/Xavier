package com.hoangson.xavier.core.helper

import com.google.gson.JsonSyntaxException
import com.hoangson.xavier.core.models.Result
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

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