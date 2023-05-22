package com.bankly.core.network

import com.bankly.core.network.model.AuthenticatedUser
import com.bankly.core.network.model.ResultMessage
import com.bankly.core.network.model.ResultStatus
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.response.TokenNetworkResponse

/**
 * Interface representing network calls to backend
 */
sealed interface RemoteDataSource {
    interface BanklyBaseDataSource {
        suspend fun forgotPassCode(
            body: ForgotPassCodeRequestBody
        ): NetworkResponse<ResultStatus>

        suspend fun validateOtp(
            body: ValidateOtpRequestBody
        ): NetworkResponse<ResultStatus>

        suspend fun resetPassCode(
            body: ResetPassCodeRequestBody
        ): NetworkResponse<ResultMessage>

        suspend fun changePassCode(
            body: ChangePassCodeRequestBody
        ): NetworkResponse<AuthenticatedUser>

        suspend fun getToken(userName: String, password: String
        ): TokenNetworkResponse
    }

    interface BanklyPosDataSource {
    }

    interface BanklyTmsDataSource {

    }

    interface BanklyNotificationDataSource {

    }

}