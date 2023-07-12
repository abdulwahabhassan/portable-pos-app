package com.bankly.core.network.retrofit.datasource

import com.bankly.core.network.RemoteDataSource
import com.bankly.core.network.model.AuthenticatedUser
import com.bankly.core.network.model.ResultMessage
import com.bankly.core.network.model.ResultStatus
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.model.request.ValidateOtpRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.response.TokenNetworkResponse
import com.bankly.core.network.retrofit.BanklyApiService
import javax.inject.Inject
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class BanklyIdentityRemoteDataSource @Inject constructor(
    private val apiService: BanklyApiService.Identity
) : RemoteDataSource.BanklyIdentityDataSource {

    override suspend fun getToken(userName: String, password: String): TokenNetworkResponse {
        return apiService.getToken(username = userName, password = password)
    }

    override suspend fun forgotPassCode(
        body: ForgotPassCodeRequestBody
    ): NetworkResponse<ResultStatus> {
        return apiService.forgotPassCode(body = body)
    }

    override suspend fun validateOtp(
        body: ValidateOtpRequestBody
    ): NetworkResponse<ResultStatus> {
        return apiService.validateOtp(body = body)
    }

    override suspend fun resetPassCode(
        body: ResetPassCodeRequestBody
    ): NetworkResponse<ResultMessage> {
        return apiService.resetPassCode(body = body)
    }

    override suspend fun changePassCode(
        body: ChangePassCodeRequestBody
    ): NetworkResponse<AuthenticatedUser> {
        return apiService.changePassCode(body = body)
    }
}