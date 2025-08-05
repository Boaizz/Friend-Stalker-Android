package com.tonyn.friend_stalker.presentation.login

import com.tonyn.friend_stalker.domain.model.User

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}