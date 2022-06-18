package com.randomuser.ui.domain

import com.randomuser.data.model.exceptions.UserNotFoundException
import com.randomuser.data.repository.RandomUserRepository
import com.randomuser.ui.model.UserDetailed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDetailedUserUseCaseImpl(private val repo: RandomUserRepository) : GetDetailedUserUseCase {
    override suspend fun execute(username: String): Flow<UserDetailed> {
        return repo.loadUser(username).map { user ->
            if (user == null) throw UserNotFoundException()
            UserDetailed(user)
        }
    }
}
