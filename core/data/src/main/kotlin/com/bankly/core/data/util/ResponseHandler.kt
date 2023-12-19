package com.bankly.core.data.util

import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.response.TokenApiResponse
import com.bankly.core.network.model.response.TransactionApiResponse
import com.bankly.core.sealed.Result

fun <T> handleApiResponse(
    requestResult: Result<ApiResponse<T>>,
): Result<T> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.successful == true && requestResult.data.hasResult == true) {
            Result.Success(data = requestResult.data.result!!)
        } else {
            Result.Error(
                message = requestResult.data.validationMessages?.joinToString(", \n")
                    ?: requestResult.data.message ?: "Request could not be completed",
            )
        }
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }

    Result.SessionExpired -> Result.SessionExpired
}

fun handleTokenApiResponse(
    requestResult: Result<TokenApiResponse>,
): Result<TokenApiResponse> = when (requestResult) {
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

    else -> Result.Error(message = "Request could not be completed")
}

fun <T> handleTransactionApiResponse(
    requestResult: Result<TransactionApiResponse<T>>,
): Result<List<T>> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.items != null) {
            Result.Success(data = requestResult.data.items ?: emptyList())
        } else {
            Result.Error(
                message = requestResult.data.message ?: "Request could not be completed",
            )
        }
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }

    Result.SessionExpired -> Result.SessionExpired
}

fun <T> handleNetworkCheckerApiResponse(
    requestResult: Result<List<T>>,
): Result<List<T>> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.isNotEmpty()) {
            Result.Success(data = requestResult.data)
        } else {
            Result.Error(
                message = "No bank network available at the moment",
            )
        }
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }

    Result.SessionExpired -> Result.SessionExpired
}
