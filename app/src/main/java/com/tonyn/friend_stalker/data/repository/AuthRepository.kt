package com.tonyn.friend_stalker.data.repository

import com.tonyn.friend_stalker.data.remote.AuthApi
import com.tonyn.friend_stalker.domain.model.User
import com.tonyn.friend_stalker.domain.repository.IAuthRepository

class AuthRepository(private val authApi: AuthApi) : IAuthRepository {
    override suspend fun login(username: String, password: String): Result<User> {
        val user = authApi.login(username, password)
        return user?.let { it }
            ?: Result.failure(Exception("Login failed"))
    }
}