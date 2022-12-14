package com.example.xxlstoremvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.xxlstoremvvm.data.models.AccountDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.xxlstoremvvm.data.network.Resource
import com.example.xxlstoremvvm.data.repository.UserRepository
import com.example.xxlstoremvvm.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<AccountDto.UserInfoResponse>> = MutableLiveData()
    val user: LiveData<Resource<AccountDto.UserInfoResponse>>
        get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

}