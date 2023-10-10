package com.bankly.core.data.repository

import com.bankly.core.data.ChangePassCodeData
import com.bankly.core.data.ForgotPassCodeData
import com.bankly.core.data.ResetPassCodeData
import com.bankly.core.data.ValidateOtpData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asAgentAccountDetails
import com.bankly.core.data.util.asMessage
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.asStatus
import com.bankly.core.data.util.asToken
import com.bankly.core.data.util.asUser
import com.bankly.core.data.util.asUserWallet
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleResponse
import com.bankly.core.data.util.handleTokenRequest
import com.bankly.core.data.util.handleTokenResponse
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.AgentAccountDetails
import com.bankly.core.entity.Message
import com.bankly.core.entity.Status
import com.bankly.core.entity.Token
import com.bankly.core.entity.User
import com.bankly.core.entity.UserWallet
import com.bankly.core.network.retrofit.service.IdentityService
import com.bankly.core.network.retrofit.service.WalletService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val identityService: IdentityService,
    private val walletService: WalletService,
) : UserRepository {

    override suspend fun getToken(
        userName: String,
        password: String,
    ): Flow<Resource<Token>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleTokenResponse(
                requestResult = handleTokenRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { identityService.getToken(username = userName, password = password) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asToken()))
        }
    }

    override suspend fun forgotPassCode(
        body: ForgotPassCodeData,
    ): Flow<Resource<Status>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { identityService.forgotPassCode(body.asRequestBody()) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asStatus()))
        }
    }

    override suspend fun validateOtp(body: ValidateOtpData): Flow<Resource<Status>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { identityService.validateOtp(body = body.asRequestBody()) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asStatus()))
        }
    }

    override suspend fun resetPassCode(
        body: ResetPassCodeData,
    ): Flow<Resource<Message>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { identityService.resetPassCode(body.asRequestBody()) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asMessage()))
        }
    }

    override suspend fun changePassCode(
        body: ChangePassCodeData,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { identityService.changePassCode(body.asRequestBody()) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asUser()))
        }
    }

    override suspend fun getWallet(
        token: String,
    ): Flow<Resource<UserWallet>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { walletService.getAgentAccount(token) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asUserWallet()))
        }
    }

    override suspend fun getAgentAccountDetails(token: String): Flow<Resource<AgentAccountDetails>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        walletService.getAgentAccount(token = token)
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asAgentAccountDetails()))
        }
    }
}
