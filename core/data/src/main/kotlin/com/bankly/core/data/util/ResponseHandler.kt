package com.bankly.core.data.util

import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.sealed.Result
import com.bankly.core.network.model.response.TokenNetworkResponse

fun <T> handleResponse(
    requestResult: Result<NetworkResponse<T>>
): Result<T> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.successful == true && requestResult.data.hasResult == true)
            Result.Success(data = requestResult.data.result!!)
        else Result.Error(
            message = requestResult.data.message ?: "Something went wrong! We're fixing it!"
        )
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }

}

fun handleTokenResponse(
    requestResult: Result<TokenNetworkResponse>
): Result<TokenNetworkResponse> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.accessToken?.isNotEmpty() == true)
            Result.Success(data = requestResult.data)
        else Result.Error(
            message = "Something went wrong! We're fixing it!"
        )
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }

}