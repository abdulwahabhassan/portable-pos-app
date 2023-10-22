package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.entity.TransactionFilterType
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionFilterTypesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        token: String,
    ): Flow<Resource<List<TransactionFilterType>>> =
        transactionRepository.getTransactionsFilterTypes(token)
}