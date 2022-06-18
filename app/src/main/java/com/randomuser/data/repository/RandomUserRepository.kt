package com.randomuser.data.repository

import com.randomuser.data.database.UserEntity
import com.randomuser.data.model.BaseApiResponse
import com.randomuser.data.model.RandomUserApiModel
import kotlinx.coroutines.flow.Flow

interface RandomUserRepository {
    suspend fun loadRandomUsersPage(page: Int): Flow<BaseApiResponse<RandomUserApiModel>>
    suspend fun loadUser(username: String): Flow<UserEntity?>
}
