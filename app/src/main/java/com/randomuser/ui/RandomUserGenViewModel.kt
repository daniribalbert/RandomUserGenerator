package com.randomuser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomuser.ui.domain.GetDetailedUserUseCase
import com.randomuser.ui.domain.GetRandomUserPageUseCase
import com.randomuser.ui.model.PagedUserList
import com.randomuser.ui.model.UserDetailed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

private const val FIRST_PAGE = 1

class RandomUserGenViewModel(
        private val getRandomUserPageUseCase: GetRandomUserPageUseCase,
        private val getDetailedUserUseCase: GetDetailedUserUseCase
) : ViewModel() {

    private val _usersListFlow = MutableStateFlow<PagedUserList?>(null)
    val usersListFlow: StateFlow<PagedUserList?> = _usersListFlow

    private val _errorFlow = MutableStateFlow<Throwable?>(null)
    val errorFlow: StateFlow<Throwable?> = _errorFlow

    private val _userDetailsFlow = MutableStateFlow<UserDetailed?>(null)
    val userDetailsFlow: StateFlow<UserDetailed?> = _userDetailsFlow

    private val isLoading = AtomicBoolean(false)

    fun loadRandomUsersPage(page: Int = FIRST_PAGE) {
        if (isLoading.get()) return
        isLoading.set(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getRandomUserPageUseCase.execute(page).collect { response ->
                    isLoading.set(false)
                    val oldUsers = _usersListFlow.value?.users ?: emptyList()
                    response.result?.let { pagedUsersList ->
                        _usersListFlow.value = PagedUserList(
                            oldUsers + pagedUsersList.users,
                            pagedUsersList.page
                        )
                    }
                    response.error?.let { error ->
                        _errorFlow.value = error
                    }
                }
            }
        }
    }

    fun loadSingleUserDetails(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getDetailedUserUseCase.execute(username).collect { user ->
                    isLoading.set(false)
                    _userDetailsFlow.value = user
                }
            }
        }
    }
}
