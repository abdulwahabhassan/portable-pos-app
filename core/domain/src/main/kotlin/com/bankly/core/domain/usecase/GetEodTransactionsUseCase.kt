package com.bankly.core.domain.usecase

import com.bankly.core.model.data.TransactionFilterData
import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.model.entity.Transaction
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEodTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        filter: com.bankly.core.model.data.TransactionFilterData,
    ): Flow<Resource<List<com.bankly.core.model.entity.Transaction>>> =
        transactionRepository.getEodTransactions(filter)
}
