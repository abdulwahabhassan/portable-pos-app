package com.bankly.core.domain.usecase

import com.bankly.core.common.model.Resource
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.model.ExternalTransfer
import com.bankly.core.model.InternalTransfer
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun performInternalTransfer(
        token: String,
        body: InternalTransfer
    ): Flow<Resource<Any>> =
        transferRepository.performInternalTransfer(token = token, body = body)

    suspend fun performExternalTransfer(
        token: String,
        body: ExternalTransfer
    ): Flow<Resource<Any>> =
        transferRepository.performExternalTransfer(token = token, body = body)
}
