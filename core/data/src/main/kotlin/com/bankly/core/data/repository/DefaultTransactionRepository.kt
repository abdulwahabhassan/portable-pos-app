package com.bankly.core.data.repository

import com.bankly.core.data.TransactionFilterData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asRequestParam
import com.bankly.core.data.util.asTransaction
import com.bankly.core.data.util.asTransactionFilterType
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleTransactionApiResponse
import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.entity.Transaction
import com.bankly.core.entity.TransactionFilterType
import com.bankly.core.network.retrofit.service.TransactionService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultTransactionRepository  @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val transactionService: TransactionService,
) : TransactionRepository {
    override suspend fun getTransactions(
        token: String,
        minimum: Long,
        maximum: Long,
        filter: TransactionFilterData
    ): Flow<Resource<List<Transaction>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleTransactionApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        transactionService.getTransactions(
                            token = token,
                            minimum = minimum,
                            maximum = maximum,
                            filter = filter.asRequestParam()
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { it.asTransaction() }))
        }
    }

    override suspend fun getEodTransactions(token: String, filter: TransactionFilterData): Flow<Resource<List<Transaction>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleTransactionApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        transactionService.getTransactions(
                            token = token,
                            minimum = 1,
                            maximum = 100,
                            filter = filter.asRequestParam()
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { it.asTransaction() }))
        }
    }

    override suspend fun getTransactionsFilterTypes(token: String): Flow<Resource<List<TransactionFilterType>>> =  flow {
        emit(Resource.Loading)
        when (
            val requestResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = {
                    transactionService.getTransactionFilterTypes(
                        token = token,
                    )
                },
            )
        ) {
            is Result.Error -> emit(Resource.Failed(requestResult.message))
            is Result.Success -> emit(Resource.Ready(requestResult.data.map { it.asTransactionFilterType() }))
        }
    }
}