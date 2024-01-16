package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.TransactionRepository
import com.bankly.core.model.entity.Transaction
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveToEodUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        transactionReceipt: TransactionReceipt
    ): Unit = transactionRepository.saveToEod(transactionReceipt)
}
