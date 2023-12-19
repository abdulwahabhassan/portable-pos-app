package com.bankly.core.domain.repository

import com.bankly.core.data.GetRecentFundingData
import com.bankly.core.data.SendReceiptData
import com.bankly.core.data.SyncRecentFundingData
import com.bankly.core.entity.RecentFund
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface PayWithTransferRepository {

    suspend fun syncRecentFunding(
        token: String,
        body: SyncRecentFundingData,
    ): Flow<Resource<String>>

    suspend fun getRecentFunding(
        token: String,
        body: GetRecentFundingData,
    ): Flow<Resource<List<RecentFund>>>

    suspend fun sendReceipt(
        token: String,
        body: SendReceiptData,
    ): Flow<Resource<String>>
}
