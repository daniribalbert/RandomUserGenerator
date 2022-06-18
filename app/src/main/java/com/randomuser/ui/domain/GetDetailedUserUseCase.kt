package com.randomuser.ui.domain

import com.randomuser.ui.model.UserDetailed
import kotlinx.coroutines.flow.Flow

interface GetDetailedUserUseCase {
    suspend fun execute(username: String): Flow<UserDetailed>
}
