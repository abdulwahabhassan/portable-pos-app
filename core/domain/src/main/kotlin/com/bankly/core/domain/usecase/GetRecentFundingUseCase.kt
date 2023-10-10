package com.bankly.core.domain.usecase

import com.bankly.core.data.GetRecentFundingData
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.entity.RecentFund
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentFundingUseCase @Inject constructor(
    private val payWithTransferRepository: PayWithTransferRepository
) {
    suspend operator fun invoke(
        token: String,
        body: GetRecentFundingData
    ): Flow<Resource<List<RecentFund>>> = payWithTransferRepository.getRecentFunding(token, body)
}