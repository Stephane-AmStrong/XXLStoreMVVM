package com.example.xxlstoremvvm.data.repository

import com.example.xxlstoremvvm.data.network.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi
) : BaseRepository(api) {

    suspend fun getUser() = safeApiCall { api.getUser() }

}