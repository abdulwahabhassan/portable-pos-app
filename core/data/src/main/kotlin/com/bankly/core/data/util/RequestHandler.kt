package com.bankly.core.data.util

import android.util.Log
import com.bankly.core.network.model.response.TokenNetworkResponse
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
            val response = handleHttpException(e, json)
            Result.Error(
                message = response?.validationMessages?.joinToString(", \n")
                    ?: response?.message ?: "Something went wrong!",
            )
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage ?: "Something went wrong!")
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
            Log.d("", "msg: ${e.message} resp: ${e.response()} error-body: ${e.response()?.errorBody()}")
            val response = handleTokenHttpException(e, json)
            Result.Error(
                message = response?.errorDescription ?: response?.error ?: response?.message
                    ?: "Something went wrong, we're fixing it",
            )
        } catch (e: Exception) {
            Result.Error(message = e.localizedMessage ?: "Something went wrong!")
        }
    }
}
