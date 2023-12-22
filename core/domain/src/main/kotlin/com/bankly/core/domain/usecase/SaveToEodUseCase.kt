package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.entity.Transaction
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveToEodUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        transactionReceipt: TransactionReceipt
    ): Unit = transactionRepository.saveToEod(transactionReceipt)
}
