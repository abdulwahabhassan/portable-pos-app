package com.bankly.core.data.repository

import com.bankly.core.common.model.Resource
import com.bankly.core.common.model.Result
import com.bankly.core.data.model.asToken
import com.bankly.core.data.model.asUser
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleResponse
import com.bankly.core.data.util.handleTokenRequest
import com.bankly.core.data.util.handleTokenResponse
import com.bankly.core.model.Token
import com.bankly.core.model.User
import com.bankly.core.network.model.request.ChangePassCodeRequestBody
import com.bankly.core.network.model.request.ForgotPassCodeRequestBody
import com.bankly.core.network.model.request.ResetPassCodeRequestBody
import com.bankly.core.network.retrofit.datasource.BanklyBaseRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json

class DefaultUserRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val banklyBaseRemoteDataSource: BanklyBaseRemoteDataSource
) : UserRepository {
    override suspend fun resetPassCode(
        body: ResetPassCodeRequestBody
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.resetPassCode(body) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asUser.invoke()))
        }
    }

    override suspend fun forgotPassCode(
        body: ForgotPassCodeRequestBody
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.forgotPassCode(body) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asUser.invoke()))
        }
    }

    override suspend fun changePassCode(
        body: ChangePassCodeRequestBody
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        when (val responseResult = handleResponse(
            requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = { banklyBaseRemoteDataSource.changePassCode(body) }
            ))) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data::asUser.invoke()))
        }
    }

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

}