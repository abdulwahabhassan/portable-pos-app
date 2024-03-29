package com.bankly.core.domain.usecase

import com.bankly.core.model.data.EodTransactionListData
import com.bankly.core.domain.repository.EndOfDayRepository
import com.bankly.core.model.entity.SyncEod
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncEodUseCase @Inject constructor(
    private val eodRepository: EndOfDayRepository,
) {
    suspend operator fun invoke(
        token: String,
        eodTransactionListData: com.bankly.core.model.data.EodTransactionListData,
    ): Flow<Resource<com.bankly.core.model.entity.SyncEod>> =
        eodRepository.syncEod(token, eodTransactionListData)
}
