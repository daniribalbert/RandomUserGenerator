package com.randomuser.ui.domain


import com.randomuser.data.model.ApiResult
import com.randomuser.data.model.mapToApiResult
import com.randomuser.data.repository.RandomUserRepository
import com.randomuser.ui.model.PagedUserList
import com.randomuser.ui.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRandomUserPageUseCaseImpl(private val repo: RandomUserRepository) : GetRandomUserPageUseCase {
    override suspend fun execute(page: Int): Flow<ApiResult<PagedUserList>> {
        return repo.loadRandomUsersPage(page)
            .mapToApiResult()
            .map { response ->
                if (response.result != null) {
                    val pagedUserList = PagedUserList(
                        response.result.results.map { User(it) },
                        response.result.info.page
                    )
                    ApiResult(pagedUserList)
                } else {
                    ApiResult(null, response.error)
                }

        }
    }

}
