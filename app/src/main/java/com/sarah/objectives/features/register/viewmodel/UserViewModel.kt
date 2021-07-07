package com.sarah.objectives.features.register.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovais.objectives.data.user.UserResponse
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.common.GeneralResponse
import com.sarah.objectives.data.token.TokenResponse
import com.sarah.objectives.data.user.User
import com.sarah.objectives.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private var repository: UserRepository) : ViewModel(), Observable {

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    private var _registerResponse = MutableLiveData<Resource<GeneralResponse>>()

    val registerResponse: LiveData<Resource<GeneralResponse>> get() = _registerResponse

    private val _tokenRequest = MutableLiveData<Resource<TokenResponse>>()

    @Bindable
    var email = MutableLiveData<String>()
    @Bindable
    var password = MutableLiveData<String>()

    val tokenResponse: LiveData<Resource<TokenResponse>> get() = _tokenRequest


    private var _userInfo = MutableLiveData<Resource<UserResponse>>()
    val userInfo:LiveData<Resource<UserResponse>> get() = _userInfo

    fun registerUser(user: User?) {
        viewModelScope.launch {
            _registerResponse.value = repository.registerUser(user!!)
        }
    }

    fun enqueueTokenRequest() {
        when {
            email.value.isNullOrBlank() -> {

            }
            password.value.isNullOrBlank() -> {

            }
            else -> {
                viewModelScope.launch {
                    _tokenRequest.value = repository.enqueueTokenRequest(email.value!!,password.value!!)
                }

            }
        }


    }

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfo.value = repository.getUserInfo()
        }
    }

}