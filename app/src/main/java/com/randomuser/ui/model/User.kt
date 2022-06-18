package com.randomuser.ui.model

import com.randomuser.data.model.RandomUserApiModel

data class User(
        val name: String,
        val thumbnail: String,
        val username: String,
        val location: String
) {
    constructor(apiModel: RandomUserApiModel) : this(
            name = apiModel.name.toString(),
            thumbnail = apiModel.picture.medium,
            username = apiModel.login.username,
            location = "${apiModel.location.city}, ${apiModel.location.country}"
    )
}
