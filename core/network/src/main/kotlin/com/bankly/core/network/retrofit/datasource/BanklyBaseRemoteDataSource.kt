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
import com.bankly.core.network.retrofit.model.BanklyBaseUrl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Singleton
class BanklyBaseRemoteDataSource @Inject constructor(
    networkJson: Json,
    okHttpClient: OkHttpClient,
) : RemoteDataSource.BanklyBaseDataSource {

    override suspend fun getToken(userName: String, password: String): TokenNetworkResponse {
        return networkApi.getToken(username = userName, password = password)
    }

    override suspend fun forgotPassCode(
        body: ForgotPassCodeRequestBody
    ): NetworkResponse<ResultStatus> {
        return networkApi.forgotPassCode(body = body)
    }

    override suspend fun validateOtp(
        body: ValidateOtpRequestBody
    ): NetworkResponse<ResultStatus> {
        return networkApi.validateOtp(body = body)
    }

    private val networkApi = Retrofit.Builder()
        .baseUrl(BanklyBaseUrl.Base.value)
        .client(okHttpClient)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(BanklyApiService.Base::class.java)

    override suspend fun resetPassCode(
        body: ResetPassCodeRequestBody
    ): NetworkResponse<ResultMessage> {
        return networkApi.resetPassCode(body = body)
    }

    override suspend fun changePassCode(
        body: ChangePassCodeRequestBody
    ): NetworkResponse<AuthenticatedUser> {
        return networkApi.changePassCode(body = body)
    }

    override suspend fun getWallet(token: String): NetworkResponse<Any> {
        return networkApi.getWallet(token = token)
    }
}