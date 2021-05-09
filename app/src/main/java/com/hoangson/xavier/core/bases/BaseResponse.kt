package com.hoangson.xavier.core.bases

data class BaseResponse<D>(
    val status : Int,
    val success: Boolean,
    val message: String,
    val Object: D? = null
) {
    fun isSuccess(): Boolean {
        return (Object != null) && status == 0
    }
}