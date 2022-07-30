package com.example.xxlstoremvvm.data.models

import java.util.*

sealed class AccountDto {

    data class AuthenticationRequest(
        val email : String,
        val password : String,
    ) : AccountDto()

    data class AuthenticationResponse(
        val userInfo : UserInfoResponse,
        val accessToken : String,
        val refreshToken : RefreshTokenResponse,
        val expireDate : Date,
        val Message: String?,
        val StatusCode: Int?
    ) : AccountDto()

    data class RefreshTokenResponse(
        val userId:String,
        val value:String,
        val expiryTime: Date,
    ) : AccountDto()

    data class UserInfoResponse(
        val name : String,
        val email : String,
    ) : AccountDto()

    data class UserRegistrationRequest(
        val confirmPassword: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val role: String
    ) : AccountDto()

    data class UserRegistrationResponse(
        val Message: String?,
        val StatusCode: Int?
    ) : AccountDto()

}