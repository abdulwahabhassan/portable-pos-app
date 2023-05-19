package com.bankly.core.data.util

import com.bankly.core.common.model.Result
import com.bankly.core.network.model.response.TokenNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException

suspend fun <T> handleRequest(
    dispatcher: CoroutineDispatcher,
    networkMonitor: NetworkMonitor,
    json: Json,
    apiRequest: suspend () -> T,
): Result<T> = withContext(dispatcher) {
    if (networkMonitor.isOnline.first()::not.invoke()) {
        Result.Error("Check your internet connection!")
    } else {
        try {
            Result.Success(apiRequest.invoke())
        } catch (e: HttpException) {
            val response = handleHttpException(e, json)
            Result.Error(
                message = response?.validationMessages?.joinToString(", \n")
                    ?: response?.message ?: "Something went wrong, we're fixing it"
            )
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage)
        }
    }
}

suspend fun handleTokenRequest(
    dispatcher: CoroutineDispatcher,
    networkMonitor: NetworkMonitor,
    json: Json,
    apiRequest: suspend () -> TokenNetworkResponse,
): Result<TokenNetworkResponse> = withContext(dispatcher) {
    if (networkMonitor.isOnline.first()::not.invoke()) {
        Result.Error("Check your internet connection!")
    } else {
        try {
            Result.Success(apiRequest.invoke())
        } catch (e: HttpException) {
            val response = handleTokenHttpException(e, json)
            Result.Error(
                message = response?.errorDescription ?: response?.error ?: response?.message
                ?: "Something went wrong, we're fixing it"
            )
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage)
        }
    }
}
