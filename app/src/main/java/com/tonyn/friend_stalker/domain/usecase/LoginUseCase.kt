package com.tonyn.friend_stalker.domain.usecase

import com.tonyn.friend_stalker.domain.model.User
import com.tonyn.friend_stalker.domain.repository.IAuthRepository

class LoginUseCase(private val repository: IAuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return repository.login(username, password)
    }
}