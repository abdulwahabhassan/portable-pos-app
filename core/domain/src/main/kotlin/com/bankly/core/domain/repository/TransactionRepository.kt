package com.bankly.core.domain.repository

import com.bankly.core.entity.Transaction
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactions(
        token: String,
        minimum: Long,
        maximum: Long,
        filter: String,
    ): Flow<Resource<List<Transaction>>>
}