package com.example.xxlstoremvvm.data.network

import com.example.xxlstoremvvm.data.responses.LoginResponse
import com.example.xxlstoremvvm.data.responses.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi : BaseApi {

    @FormUrlEncoded
    @POST("account/authenticate")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}