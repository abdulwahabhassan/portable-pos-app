package com.bankly.core.data.repository

import com.bankly.core.model.data.ChangePassCodeData
import com.bankly.core.model.data.ForgotPassCodeData
import com.bankly.core.model.data.ForgotTerminalAccessPinData
import com.bankly.core.model.data.ResetPassCodeData
import com.bankly.core.model.data.ValidateOtpData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asAgentAccountDetails
import com.bankly.core.data.util.asMessage
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.asStatus
import com.bankly.core.data.util.asToken
import com.bankly.core.data.util.asUser
import com.bankly.core.data.util.asUserWallet
import com.bankly.core.data.util.getUsernameFromAccessToken
import com.bankly.core.data.util.handleApiResponse
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleTokenApiResponse
import com.bankly.core.data.util.handleTokenRequest
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.entity.AgentAccountDetails
import com.bankly.core.model.entity.Message
import com.bankly.core.model.entity.Status
import com.bankly.core.model.entity.Token
import com.bankly.core.model.entity.User
import com.bankly.core.model.entity.UserWallet
import com.bankly.core.network.retrofit.service.IdentityService
import com.bankly.core.network.retrofit.service.WalletService
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.Result
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
    ): Flow<Resource<com.bankly.core.model.entity.Token>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleTokenApiResponse(
                requestResult = handleTokenRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        identityService.getToken(
                            username = userName,
                            password = password,
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asToken()))
            else -> {}
        }
    }

    override suspend fun forgotPassCode(
        body: com.bankly.core.model.data.ForgotPassCodeData,
    ): Flow<Resource<com.bankly.core.model.entity.Status>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun forgotTerminalAccessPin(
        body: com.bankly.core.model.data.ForgotTerminalAccessPinData,
    ): Flow<Resource<com.bankly.core.model.entity.Status>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { identityService.forgotTerminalAccessPin(body.asRequestBody()) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asStatus()))
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun validateOtp(body: com.bankly.core.model.data.ValidateOtpData): Flow<Resource<com.bankly.core.model.entity.Status>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun resetPassCode(
        body: com.bankly.core.model.data.ResetPassCodeData,
    ): Flow<Resource<com.bankly.core.model.entity.Message>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun changePassCode(
        body: com.bankly.core.model.data.ChangePassCodeData,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getWallet(
        token: String,
    ): Flow<Resource<UserWallet>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getAgentAccountDetails(token: String): Flow<Resource<com.bankly.core.model.entity.AgentAccountDetails>> =
        flow {
            emit(Resource.Loading)
            when (
                val responseResult = handleApiResponse(
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
                Result.SessionExpired -> emit(Resource.SessionExpired)
            }
        }

    override suspend fun validatePassCode(password: String, token: String): Flow<Resource<com.bankly.core.model.entity.Token>> =
        flow {
            emit(Resource.Loading)
            val userName = getUsernameFromAccessToken(token)
            if (userName == null) {
                emit(Resource.Failed("Username could not be found. Please try again"))
            } else {
                when (
                    val responseResult = handleTokenApiResponse(
                        requestResult = handleTokenRequest(
                            dispatcher = ioDispatcher,
                            networkMonitor = networkMonitor,
                            json = json,
                            apiRequest = { identityService.getToken(username = userName, password = password, authMode = null) },
                        ),
                    )
                ) {
                    is Result.Error -> emit(Resource.Failed(responseResult.message))
                    is Result.Success -> emit(Resource.Ready(responseResult.data.asToken()))
                    else -> {}
                }
            }
        }
}
