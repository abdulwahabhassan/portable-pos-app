package com.bankly.core.data.repository

import com.bankly.core.common.model.Resource
import com.bankly.core.common.model.Result
import com.bankly.core.data.util.asMessage
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.asStatus
import com.bankly.core.data.util.asToken
import com.bankly.core.data.util.asUser
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleResponse
import com.bankly.core.data.util.handleTokenRequest
import com.bankly.core.data.util.handleTokenResponse
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.ChangePassCode
import com.bankly.core.model.ForgotPassCode
import com.bankly.core.model.Message
import com.bankly.core.model.ResetPassCode
import com.bankly.core.model.Status
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.model.ValidateOtp
import com.bankly.core.network.retrofit.datasource.BanklyBaseRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class DefaultUserRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val banklyBaseRemoteDataSource: BanklyBaseRemoteDataSource
) : UserRepository {

    override suspend fun getToken(
        userName: String, password: String
    ): Flow<Resource<Token>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleTokenResponse(
            requestResult = handleTokenRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.getToken(userName, password) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asToken.invoke()))
        }
    }

    override suspend fun forgotPassCode(
        body: ForgotPassCode
    ): Flow<Resource<Status>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.forgotPassCode(body.asRequestBody()) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asStatus.invoke()))
        }
    }

    override suspend fun validateOtp(body: ValidateOtp): Flow<Resource<Status>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.validateOtp(body = body.asRequestBody()) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asStatus.invoke()))
        }
    }


    override suspend fun resetPassCode(
        body: ResetPassCode
    ): Flow<Resource<Message>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.resetPassCode(body.asRequestBody()) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asMessage.invoke()))
        }
    }

    override suspend fun changePassCode(
        body: ChangePassCode
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.changePassCode(body.asRequestBody()) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asUser.invoke()))
        }
    }

    override suspend fun getWallet(
        token: String
    ): Flow<Resource<Any>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.getWallet(token) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data))
        }
    }
}