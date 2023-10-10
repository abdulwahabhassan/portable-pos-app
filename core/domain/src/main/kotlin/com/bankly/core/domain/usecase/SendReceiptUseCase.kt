package com.bankly.core.domain.usecase

import com.bankly.core.data.SendReceiptData
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendReceiptUseCase @Inject constructor(
    private val payWithTransferRepository: PayWithTransferRepository
) {
    suspend operator fun invoke(
        token: String,
        body: SendReceiptData
    ): Flow<Resource<String>> = payWithTransferRepository.sendReceipt(token, body)
}