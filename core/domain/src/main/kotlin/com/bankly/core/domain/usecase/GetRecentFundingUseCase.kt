package com.bankly.core.domain.usecase

import com.bankly.core.model.data.GetRecentFundingData
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.model.entity.RecentFund
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentFundingUseCase @Inject constructor(
    private val payWithTransferRepository: PayWithTransferRepository,
) {
    suspend operator fun invoke(
        token: String,
        body: com.bankly.core.model.data.GetRecentFundingData,
    ): Flow<Resource<List<com.bankly.core.model.entity.RecentFund>>> = payWithTransferRepository.getRecentFunding(token, body)
}
