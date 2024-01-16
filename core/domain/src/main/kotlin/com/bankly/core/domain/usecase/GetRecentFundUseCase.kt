package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.model.entity.RecentFund
import javax.inject.Inject

class GetRecentFundUseCase @Inject constructor(
    private val payWithTransferRepository: PayWithTransferRepository,
) {
    suspend operator fun invoke(
        transactionRef: String,
        sessionId: String,
    ): RecentFund? = payWithTransferRepository.getRecentFund(transactionRef, sessionId)
}