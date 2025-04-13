package com.rasel.baseappcompose.data.model.signin

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class UserInfoResponse(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("role")
    val role: String
)

data class ErrorResponse(
    val code: Int,
    val message: String
)