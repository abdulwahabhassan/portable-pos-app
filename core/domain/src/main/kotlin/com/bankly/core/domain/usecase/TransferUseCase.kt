package com.bankly.core.domain.usecase

import com.bankly.core.data.ExternalTransferData
import com.bankly.core.data.InternalTransferData
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Transaction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun performInternalTransfer(
        token: String,
        body: InternalTransferData
    ): Flow<Resource<Any>> =
        transferRepository.performInternalTransfer(token = token, body = body)

    suspend fun performExternalTransfer(
        token: String,
        body: ExternalTransferData
    ): Flow<Resource<Transaction.External>> =
        transferRepository.performExternalTransfer(token = token, body = body)
}
