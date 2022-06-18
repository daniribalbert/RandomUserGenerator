package com.randomuser.data.model

data class RandomUserApiModel(
        val gender: String,
        val name: UsernameApiModel,
        val location: UserLocationApiModel,
        val email: String,
        val login: LoginInfoApiModel,
        val dob: DateOfBirthApiModel,
        val phone: String,
        val cell: String,
        val picture: UserPictureApiModel
)


