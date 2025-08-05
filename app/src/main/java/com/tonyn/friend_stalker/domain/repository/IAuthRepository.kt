package com.tonyn.friend_stalker.domain.repository

import com.tonyn.friend_stalker.domain.model.User

interface IAuthRepository {
    suspend fun login(username: String, password: String): Result<User>
}