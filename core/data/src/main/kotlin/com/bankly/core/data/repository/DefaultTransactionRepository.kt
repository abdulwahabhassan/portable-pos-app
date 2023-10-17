package com.bankly.core.data.repository

import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.asTransaction
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleResponse
import com.bankly.core.data.util.handleTransactionResponse
import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.entity.Transaction
import com.bankly.core.network.retrofit.service.TransactionService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        filter: String
    ): Flow<Resource<List<Transaction>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleTransactionResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        transactionService.getTransactions(
                            token = token,
                            minimum = minimum,
                            maximum = maximum,
                            filter = filter
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { it.asTransaction() }))
        }
    }
}