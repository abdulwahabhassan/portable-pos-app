package com.bankly.core.data.util

import com.bankly.core.network.model.response.ApiError
import com.bankly.core.network.model.response.TokenApiError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.nio.charset.Charset

fun handleHttpException(e: HttpException, json: Json): ApiError? {
    return try {
        e.response()?.errorBody()?.source()?.readString(Charset.defaultCharset())?.let {
            json.decodeFromString<ApiError>(it)
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}

fun handleTokenHttpException(e: HttpException, json: Json): TokenApiError? {
    return try {
        e.response()?.errorBody()?.source()?.readString(Charset.defaultCharset())?.let {
            json.decodeFromString<TokenApiError>(it)
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}
