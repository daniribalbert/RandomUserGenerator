package com.randomuser.ui.domain

import com.randomuser.data.model.ApiResult
import com.randomuser.ui.model.PagedUserList
import kotlinx.coroutines.flow.Flow

interface GetRandomUserPageUseCase {
    suspend fun execute(page: Int): Flow<ApiResult<PagedUserList>>
}
