package com.example.xxlstoremvvm.data.network

import com.example.xxlstoremvvm.data.models.AccountDto
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationApi : BaseApi {

    @POST("authentication/registration")
    suspend fun registerUser(@Body userRegistrationRequest: AccountDto.UserRegistrationRequest): AccountDto.UserRegistrationResponse


    //@FormUrlEncoded
    @POST("account/authenticate")
    suspend fun authenticate(
        @Body authentication: AccountDto.AuthenticationRequest
    ): AccountDto.AuthenticationResponse
}