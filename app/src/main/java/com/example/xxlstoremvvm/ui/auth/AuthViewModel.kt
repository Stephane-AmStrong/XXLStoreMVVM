package com.example.xxlstoremvvm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.xxlstoremvvm.data.models.AccountDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.xxlstoremvvm.data.network.Resource
import com.example.xxlstoremvvm.data.repository.AuthRepository
import com.example.xxlstoremvvm.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _authenticationResponse: MutableLiveData<Resource<AccountDto.AuthenticationResponse>> = MutableLiveData()
    val authenticationResponse: LiveData<Resource<AccountDto.AuthenticationResponse>>
        get() = _authenticationResponse

    fun authentication(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _authenticationResponse.value = Resource.Loading
        _authenticationResponse.value = repository.authenticate(email, password)
    }

    suspend fun saveAccessTokens(authenticationResponse: AccountDto.AuthenticationResponse) {
        repository.saveAccessTokens(authenticationResponse)
    }
}