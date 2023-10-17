package com.bankly.core.data.util

import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.response.TokenNetworkResponse
import com.bankly.core.network.model.response.TransactionResponse
import com.bankly.core.sealed.Result

fun <T> handleResponse(
    requestResult: Result<NetworkResponse<T>>,
): Result<T> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.successful == true && requestResult.data.hasResult == true) {
            Result.Success(data = requestResult.data.result!!)
        } else {
            Result.Error(
                message =  requestResult.data.validationMessages?.joinToString(", \n")
                    ?: requestResult.data.message ?: "Request could not be completed",
            )
        }
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }
}

fun handleTokenResponse(
    requestResult: Result<TokenNetworkResponse>,
): Result<TokenNetworkResponse> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.accessToken?.isNotEmpty() == true) {
            Result.Success(data = requestResult.data)
        } else {
            Result.Error(
                message = "Request could not be completed",
            )
        }
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }
}

fun <T> handleTransactionResponse(
    requestResult: Result<TransactionResponse<T>>,
): Result<List<T>> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.items != null) {
            Result.Success(data = requestResult.data.items ?: emptyList())
        } else {
            Result.Error(
                message =  requestResult.data.message ?: "Request could not be completed",
            )
        }
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }
}
