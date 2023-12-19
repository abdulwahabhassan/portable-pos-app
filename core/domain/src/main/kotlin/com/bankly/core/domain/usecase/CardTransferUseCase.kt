package com.bankly.core.domain.usecase

import com.bankly.core.data.CardTransferData
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardTransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun invoke(
        token: String,
        body: CardTransferData,
    ): Flow<Resource<TransactionReceipt.CardTransfer>> =
        transferRepository.performCardTransfer(token = token, body = body)
}
