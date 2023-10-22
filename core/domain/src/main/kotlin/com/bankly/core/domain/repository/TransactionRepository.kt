package com.bankly.core.domain.repository

import com.bankly.core.data.TransactionFilterData
import com.bankly.core.entity.Transaction
import com.bankly.core.entity.TransactionFilterType
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactions(
        token: String,
        minimum: Long,
        maximum: Long,
        filter: TransactionFilterData,
    ): Flow<Resource<List<Transaction>>>

    suspend fun getTransactionsFilterTypes(
        token: String,
    ): Flow<Resource<List<TransactionFilterType>>>
}