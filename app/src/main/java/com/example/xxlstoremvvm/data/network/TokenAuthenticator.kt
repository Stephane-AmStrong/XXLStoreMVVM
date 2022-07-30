package com.example.xxlstoremvvm.data.network

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.example.xxlstoremvvm.data.UserPreferences
import com.example.xxlstoremvvm.data.models.AccountDto
import com.example.xxlstoremvvm.data.repository.BaseRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    context: Context,
    private val tokenApi: TokenRefreshApi
) : Authenticator, BaseRepository(tokenApi) {

    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val tokenResponse = getUpdatedToken()) {
                is Resource.Success -> {
                    userPreferences.saveAccessTokens(
                        tokenResponse.value
                    )
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokenResponse.value}")
                        .build()
                }
                else -> null
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getUpdatedToken(): Resource<AccountDto.AuthenticationResponse> {
        val authenticationResponse = userPreferences.authenticationResponse.first()
        return safeApiCall {
            tokenApi.refreshAccessToken(accessToken = authenticationResponse.accessToken, refreshToken = authenticationResponse.refreshToken.value)
        }
    }

}