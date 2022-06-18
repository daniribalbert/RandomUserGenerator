package com.randomuser.data.model

data class BaseApiResponse<T>(
        val results: List<T>,
        val info: InfoApiModel
)
