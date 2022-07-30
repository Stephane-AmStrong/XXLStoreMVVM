package com.example.xxlstoremvvm.data.repository

import com.example.xxlstoremvvm.data.network.BaseApi
import com.example.xxlstoremvvm.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {

    suspend fun logout() = safeApiCall {
        api.logout()
    }
}