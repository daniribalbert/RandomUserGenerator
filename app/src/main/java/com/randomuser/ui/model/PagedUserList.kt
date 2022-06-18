package com.randomuser.ui.model

data class PagedUserList(
        val users: List<User>,
        val page: Int
)
