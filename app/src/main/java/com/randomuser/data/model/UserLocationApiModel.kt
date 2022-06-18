package com.randomuser.data.model

data class UserLocationApiModel(
        val street: StreetApiModel,
        val city: String,
        val state: String,
        val country: String
)

data class StreetApiModel(
        val number: Int,
        val name: String
) {
    override fun toString(): String {
        return "$number $name"
    }
}
