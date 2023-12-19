package com.bankly.core.domain.usecase

import com.bankly.core.data.EodTransactionListData
import com.bankly.core.domain.repository.EndOfDayRepository
import com.bankly.core.entity.SyncEod
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncEodUseCase @Inject constructor(
    private val eodRepository: EndOfDayRepository,
) {
    suspend operator fun invoke(
        token: String,
        eodTransactionListData: EodTransactionListData,
    ): Flow<Resource<SyncEod>> =
        eodRepository.syncEod(token, eodTransactionListData)
}
