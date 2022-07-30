package com.example.xxlstoremvvm.data.network

import com.example.xxlstoremvvm.data.models.AccountDto
import retrofit2.http.GET

interface UserApi : BaseApi{
    @GET("user")
    suspend fun getUser(): AccountDto.UserInfoResponse
}