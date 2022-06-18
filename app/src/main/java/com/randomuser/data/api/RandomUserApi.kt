package com.randomuser.data.api

import com.randomuser.data.model.BaseApiResponse
import com.randomuser.data.model.RandomUserApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("api")
    suspend fun getRandomUsersPage(@Query("page") page: Int, @Query("results") results: Int = 20): BaseApiResponse<RandomUserApiModel>
}
