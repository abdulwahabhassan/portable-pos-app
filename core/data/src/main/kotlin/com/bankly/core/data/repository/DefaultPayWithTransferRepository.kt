package com.bankly.core.data.repository

import com.bankly.core.data.GetRecentFundingData
import com.bankly.core.data.SendReceiptData
import com.bankly.core.data.SyncRecentFundingData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asAgentAccountDetails
import com.bankly.core.data.util.asRecentFund
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleResponse
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.entity.AgentAccountDetails
import com.bankly.core.entity.RecentFund
import com.bankly.core.network.model.result.RecentFundResult
import com.bankly.core.network.retrofit.service.PayWithTransferService
import com.bankly.core.network.retrofit.service.WalletService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultPayWithTransferRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val payWithTransferService: PayWithTransferService,
    private val walletService: WalletService,
) : PayWithTransferRepository {
    override suspend fun syncRecentFunding(
        token: String,
        body: SyncRecentFundingData
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        payWithTransferService.syncRecentFunding(
                            token = token,
                            body = body.asRequestBody(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data))
        }
    }

    override suspend fun getRecentFunding(
        token: String,
        body: GetRecentFundingData
    ): Flow<Resource<List<RecentFund>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        payWithTransferService.getRecentFunding(
                            token = token,
                            body = body.asRequestBody(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { recentFundResult: RecentFundResult -> recentFundResult.asRecentFund() }))
        }
    }

    override suspend fun sendReceipt(
        token: String,
        body: SendReceiptData
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        payWithTransferService.sendReceipt(token = token, body = body.asRequestBody())
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data))
        }
    }
}