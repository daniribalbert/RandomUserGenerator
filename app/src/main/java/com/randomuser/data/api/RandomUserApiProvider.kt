package com.randomuser.data.api

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import retrofit2.Retrofit

class RandomUserApiProvider : KoinComponent {

    fun getApi(): RandomUserApi {
        val retrofit = get<Retrofit>()
        return retrofit.create(RandomUserApi::class.java)
    }

}
