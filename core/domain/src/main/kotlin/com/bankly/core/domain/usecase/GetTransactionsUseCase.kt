package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.entity.Transaction
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        token: String,
        minimum: Long,
        maximum: Long,
        filter: String,
    ): Flow<Resource<List<Transaction>>> =
        transactionRepository.getTransactions(token, minimum, maximum, filter)
}