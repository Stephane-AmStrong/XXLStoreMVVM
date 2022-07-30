package com.example.xxlstoremvvm.data.network

import com.example.xxlstoremvvm.data.models.AccountDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRefreshApi : BaseApi {
    @FormUrlEncoded
    @POST("account/refresh-token")
    suspend fun refreshAccessToken(
        @Field("accessToken") accessToken: String,
        @Field("refreshToken") refreshToken: String
    ): AccountDto.AuthenticationResponse
}