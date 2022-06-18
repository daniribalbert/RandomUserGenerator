package com.randomuser.data.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

data class ApiResult<T>(val result: T?, val error: Throwable? = null)

fun <T> Flow<T>.mapToApiResult(): Flow<ApiResult<T>> = this.map { result ->
    ApiResult(result, null)
}.catch { error ->
    emit(ApiResult(null, error))
}

