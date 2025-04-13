package com.rasel.baseappcompose.data.network.retrofit

import com.rasel.baseappcompose.data.model.signin.Auth
import com.rasel.baseappcompose.data.model.signin.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(
        @Body auth: Auth,
    ): Response<LoginResponse>

    @GET("auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<LoginResponse>
}