package com.rasel.baseappcompose.data.model.signin

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("email")
    val email: String,
    val password: String
)