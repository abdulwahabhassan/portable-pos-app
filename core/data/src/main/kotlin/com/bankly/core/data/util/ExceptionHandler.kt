package com.bankly.core.data.util

import com.bankly.core.network.model.response.NetworkError
import com.bankly.core.network.model.response.TokenNetworkError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.nio.charset.Charset

fun handleHttpException(e: HttpException, json: Json): NetworkError? {
    return try {
        e.response()?.errorBody()?.source()?.readString(Charset.defaultCharset())?.let {
            json.decodeFromString<NetworkError>(it)
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}

fun handleTokenHttpException(e: HttpException, json: Json): TokenNetworkError? {
    return try {
        e.response()?.errorBody()?.source()?.readString(Charset.defaultCharset())?.let {
            json.decodeFromString<TokenNetworkError>(it)
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}
