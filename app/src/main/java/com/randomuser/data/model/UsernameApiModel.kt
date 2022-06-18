package com.randomuser.data.model

data class UsernameApiModel(
        val title: String,
        val first: String,
        val last: String
) {
    override fun toString(): String {
        return "$title $first $last"
    }
}
