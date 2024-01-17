package com.bankly.core.data.repository

import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asRecentFund
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.handleApiResponse
import com.bankly.core.data.util.handleRequest
import com.bankly.core.database.dao.RecentFundDao
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.model.entity.RecentFund
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.Result
import com.bankly.core.network.model.result.RecentFundResult
import com.bankly.core.network.retrofit.service.PayWithTransferService
import com.bankly.core.network.retrofit.service.WalletService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultPayWithTransferRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val payWithTransferService: PayWithTransferService,
    private val recentFundDao: RecentFundDao,
) : PayWithTransferRepository {
    override suspend fun syncRecentFunding(
        token: String,
        body: com.bankly.core.model.data.SyncRecentFundingData,
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getRemoteRecentFunds(
        token: String,
        body: com.bankly.core.model.data.GetRecentFundingData,
    ): Flow<Resource<List<RecentFund>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getLocalRecentFunds(): Flow<List<RecentFund>> =
        recentFundDao.getRecentFunds()
            .map { recentFundList -> recentFundList.map { it.asRecentFund() } }

    override suspend fun sendReceipt(
        token: String,
        body: com.bankly.core.model.data.SendReceiptData,
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        payWithTransferService.sendReceipt(
                            token = token,
                            body = body.asRequestBody()
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data))
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun insertRecentFund(recentFund: RecentFund) {
        recentFundDao.insertRecentFund(recentFund.asRecentFund())
    }

    override suspend fun getRecentFund(transactionRef: String, sessionId: String): RecentFund? {
        return recentFundDao.getRecentFund(transactionRef, sessionId)?.asRecentFund()
    }

}
