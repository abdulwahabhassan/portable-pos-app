package com.bankly.core.network

import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.response.TokenNetworkResponse
import com.bankly.core.network.model.result.AuthenticatedUserResult
import com.bankly.core.network.model.result.MessageResult
import com.bankly.core.network.model.result.StatusResult
import com.bankly.core.network.model.result.WalletResult

/**
 * Interface representing network calls to backend
 */
interface IdentityServiceDataSource {

    suspend fun getToken(
        userName: String,
        password: String,
    ): TokenNetworkResponse

    suspend fun forgotPassCode(
        body: ForgotPassCodeRequestBody,
    ): NetworkResponse<StatusResult>

    suspend fun validateOtp(
        body: ValidateOtpRequestBody,
    ): NetworkResponse<StatusResult>

    suspend fun resetPassCode(
        body: ResetPassCodeRequestBody,
    ): NetworkResponse<MessageResult>

    suspend fun changePassCode(
        body: ChangePassCodeRequestBody,
    ): NetworkResponse<AuthenticatedUserResult>
}

interface BanklyWalletDataSource {
    suspend fun getWallet(
        token: String,
    ): NetworkResponse<WalletResult>
}

interface BanklyPosDataSource

interface BanklyTmsDataSource

interface BanklyNotificationDataSource
