package com.bankly.core.domain.repository

import com.bankly.core.data.EodTransactionListData
import com.bankly.core.entity.EodInfo
import com.bankly.core.entity.SyncEod
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface EndOfDayRepository {
    suspend fun getEodInfo(
        token: String,
    ): Flow<Resource<EodInfo>>

    suspend fun syncEod(
        token: String,
        eodTransactionListData: EodTransactionListData
    ): Flow<Resource<SyncEod>>
}