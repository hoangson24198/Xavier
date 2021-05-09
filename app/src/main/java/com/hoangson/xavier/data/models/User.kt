package com.hoangson.xavier.data.models

data class User(
    val id : Long,
    val username : String,
    val password : String,
    val display_name : String,
    val avatar : String,
    val gender : Int
)
