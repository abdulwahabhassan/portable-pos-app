package com.bankly.core.domain.usecase

import com.bankly.core.model.data.CardTransferData
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardTransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun invoke(
        token: String,
        body: com.bankly.core.model.data.CardTransferData,
    ): Flow<Resource<TransactionReceipt.CardTransfer>> =
        transferRepository.performCardTransfer(token = token, body = body)
}
