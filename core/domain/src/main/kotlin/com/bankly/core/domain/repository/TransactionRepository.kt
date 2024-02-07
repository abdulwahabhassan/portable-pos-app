package com.bankly.core.domain.repository

import com.bankly.core.model.data.TransactionFilterData
import com.bankly.core.model.entity.Transaction
import com.bankly.core.model.entity.TransactionFilterType
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactions(
        token: String,
        minimum: Long,
        maximum: Long,
        filter: TransactionFilterData,
    ): Flow<Resource<List<Transaction>>>

    suspend fun getEodTransactions(
        filter: TransactionFilterData,
    ): Flow<Resource<List<Transaction>>>

    suspend fun getTransactionsFilterTypes(
        token: String,
    ): Flow<Resource<List<TransactionFilterType>>>

    suspend fun saveToEod(
        transactionReceipt: TransactionReceipt
    )
}
