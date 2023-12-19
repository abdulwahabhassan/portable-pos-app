package com.bankly.core.data.util

import com.bankly.core.network.model.response.TokenApiResponse
import com.bankly.core.sealed.Result
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
            if (e.code() == 401) {
//                Result.Error(message = "Session expired! Please logout and login again to continue")
                Result.SessionExpired
            } else {
                val response = handleHttpException(e, json)
                Result.Error(
                    message = response?.validationMessages?.joinToString(", \n")
                        ?: response?.message ?: "Request could not be completed",
                )
            }
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage ?: "Request could not be completed")
        }
    }
}

suspend fun handleTokenRequest(
    dispatcher: CoroutineDispatcher,
    networkMonitor: NetworkMonitor,
    json: Json,
    apiRequest: suspend () -> TokenApiResponse,
): Result<TokenApiResponse> = withContext(dispatcher) {
    if (networkMonitor.isOnline.first()::not.invoke()) {
        Result.Error("Check your internet connection!")
    } else {
        try {
            Result.Success(apiRequest.invoke())
        } catch (e: HttpException) {
            val response = handleTokenHttpException(e, json)
            Result.Error(
                message = if (response?.status == 3L || response?.status == 4L) {
                    response.status.toString()
                } else {
                    response?.errorDescription
                        ?: response?.error
                        ?: response?.message
                        ?: "Request could not be completed"
                },
            )
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage ?: "Request could not be completed")
        }
    }
}
