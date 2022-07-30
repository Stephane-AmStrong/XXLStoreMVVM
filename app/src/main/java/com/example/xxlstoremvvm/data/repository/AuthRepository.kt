package com.example.xxlstoremvvm.data.repository

import com.example.xxlstoremvvm.data.UserPreferences
import com.example.xxlstoremvvm.data.models.AccountDto
import com.example.xxlstoremvvm.data.network.AuthenticationApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthenticationApi,
    private val preferences: UserPreferences
) : BaseRepository(api) {

    suspend fun authenticate(
        email: String,
        password: String
    ) = safeApiCall {
        api.authenticate(AccountDto.AuthenticationRequest(email, password))
    }

    suspend fun saveAccessTokens(authenticationResponse: AccountDto.AuthenticationResponse) {
        preferences.saveAccessTokens(authenticationResponse)
    }

}