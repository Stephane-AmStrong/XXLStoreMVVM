package com.example.xxlstoremvvm.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.xxlstoremvvm.data.models.AccountDto
import com.example.xxlstoremvvm.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    private val appContext = context.applicationContext

    suspend fun saveAccessTokens(authenticationResponse: AccountDto.AuthenticationResponse) {
        appContext.dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = authenticationResponse.accessToken
            preferences[KEY_EXPIRE_DATE] = authenticationResponse.expireDate.time

            preferences[KEY_USER_NAME] = authenticationResponse.userInfo.name
            preferences[KEY_USER_EMAIL] = authenticationResponse.userInfo.email

            preferences[KEY_REFRESH_TOKEN_VALUE] = authenticationResponse.refreshToken.value
            preferences[KEY_REFRESH_TOKEN_USER_ID] = authenticationResponse.refreshToken.userId
            preferences[KEY_REFRESH_TOKEN_EXPIRY_TIME] =
                authenticationResponse.refreshToken.expiryTime.time
        }
    }

    val accessToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[KEY_ACCESS_TOKEN]
        }

    val expireDate: Flow<Long?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[KEY_EXPIRE_DATE]
        }

    val userInfo: Flow<AccountDto.UserInfoResponse>
        get() =
            appContext.dataStore.data.map { preferences ->
                AccountDto.UserInfoResponse(
                    preferences[KEY_USER_NAME]!!,
                    preferences[KEY_USER_EMAIL]!!,
                )
            }

    val authenticationResponse: Flow<AccountDto.AuthenticationResponse>
        @RequiresApi(Build.VERSION_CODES.O)
        get() = appContext.dataStore.data.map { preferences ->
            AccountDto.AuthenticationResponse(
                userInfo = AccountDto.UserInfoResponse(
                    name = preferences[KEY_USER_NAME]!!,
                    email = preferences[KEY_USER_EMAIL]!!,
                ),
                accessToken = preferences[KEY_ACCESS_TOKEN]!!,
                refreshToken = AccountDto.RefreshTokenResponse(
                    userId = preferences[KEY_REFRESH_TOKEN_USER_ID]!!,
                    value = preferences[KEY_REFRESH_TOKEN_VALUE]!!,
                    expiryTime = preferences[KEY_REFRESH_TOKEN_EXPIRY_TIME]!!.toLocalDate()
                        .toDate(),
                ),
                expireDate = preferences[KEY_EXPIRE_DATE]!!.toLocalDate().toDate(),
                null,
                null
            )
        }

    suspend fun clear() {
        appContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        private val KEY_EXPIRE_DATE = longPreferencesKey("key_expire_date")

        private val KEY_USER_NAME = stringPreferencesKey("key_user_name")
        private val KEY_USER_EMAIL = stringPreferencesKey("key_user_email")

        private val KEY_REFRESH_TOKEN_VALUE = stringPreferencesKey("key_refresh_token_value")
        private val KEY_REFRESH_TOKEN_USER_ID = stringPreferencesKey("key_refresh_token_user_id")
        private val KEY_REFRESH_TOKEN_EXPIRY_TIME = longPreferencesKey("key_refresh_token_expiry_time")
    }
}