package com.bankly.core.domain.usecase

import com.bankly.core.data.SyncRecentFundingData
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncRecentFundingUseCase @Inject constructor(
    private val payWithTransferRepository: PayWithTransferRepository
) {
    suspend operator fun invoke(
        token: String,
        body: SyncRecentFundingData
    ): Flow<Resource<String>> = payWithTransferRepository.syncRecentFunding(token, body)
}