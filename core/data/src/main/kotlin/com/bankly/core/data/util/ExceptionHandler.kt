package com.bankly.core.data.util

import com.bankly.core.network.model.response.NetworkError
import com.bankly.core.network.model.response.TokenNetworkError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

fun handleHttpException(e: HttpException, json: Json): NetworkError? {
    return try {
        e.response()?.errorBody()?.let {
            json.decodeFromString<NetworkError>(e.response()?.errorBody().toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}

fun handleTokenHttpException(e: HttpException, json: Json): TokenNetworkError? {
    return try {
        e.response()?.errorBody()?.let {
            json.decodeFromString<TokenNetworkError>(e.response()?.errorBody().toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}
