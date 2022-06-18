package com.randomuser.ui.model

import com.randomuser.data.database.UserEntity

data class UserDetailed(
        val name: String,
        val gender: String,
        val email: String,
        val age: Int,
        val image: String,
        val street: String,
        val city: String,
        val country: String,
        val cell: String,
) {
    constructor(userEntity: UserEntity) : this(
            userEntity.name,
            userEntity.gender,
            userEntity.email,
            userEntity.age,
            userEntity.largePicture,
            userEntity.street,
            userEntity.city,
            userEntity.country,
            userEntity.cell
    )
}
