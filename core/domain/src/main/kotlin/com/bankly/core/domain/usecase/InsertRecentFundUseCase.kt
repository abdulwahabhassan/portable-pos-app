package com.bankly.core.domain.usecase

import android.util.Log
import com.bankly.core.domain.repository.PayWithTransferRepository
import com.bankly.core.model.entity.RecentFund
import javax.inject.Inject

class InsertRecentFundUseCase@Inject constructor(
    private val payWithTransferRepository: PayWithTransferRepository,
) {
    suspend operator fun invoke(
        recentFund: RecentFund,
    ): Unit {
        Log.d("debug", "invoke InsertRecentFundUseCase, recent fund: $recentFund")
        payWithTransferRepository.insertRecentFund(recentFund)
    }
}