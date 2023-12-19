package com.bankly.core.domain.usecase

import com.bankly.core.data.TransactionFilterData
import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.entity.Transaction
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEodTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        token: String,
        filter: TransactionFilterData,
    ): Flow<Resource<List<Transaction>>> =
        transactionRepository.getEodTransactions(token, filter)
}
