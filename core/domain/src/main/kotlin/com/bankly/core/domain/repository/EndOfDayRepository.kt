package com.bankly.core.domain.repository

import com.bankly.core.model.data.EodTransactionListData
import com.bankly.core.model.entity.EodInfo
import com.bankly.core.model.entity.SyncEod
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface EndOfDayRepository {
    suspend fun getEodInfo(
        token: String,
    ): Flow<Resource<com.bankly.core.model.entity.EodInfo>>

    suspend fun syncEod(
        token: String,
        eodTransactionListData: com.bankly.core.model.data.EodTransactionListData,
    ): Flow<Resource<com.bankly.core.model.entity.SyncEod>>
}
