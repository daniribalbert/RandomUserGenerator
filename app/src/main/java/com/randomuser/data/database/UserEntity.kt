package com.randomuser.data.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class UserEntity(
        @Id
        var id: Long? = null,
        val name: String,
        val gender: String,
        val email: String,
        val age: Int,
        val birthdate: String,
        val phone: String,
        val cell: String,
        val thumbnail: String,
        val largePicture: String,
        val street: String,
        val city: String,
        val state: String,
        val country: String,
        val uuid: String,
        val username: String,
        val password: String
)

