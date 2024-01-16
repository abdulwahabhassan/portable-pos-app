package com.bankly.core.domain.repository

import com.bankly.core.model.data.GetRecentFundingData
import com.bankly.core.model.data.SendReceiptData
import com.bankly.core.model.data.SyncRecentFundingData
import com.bankly.core.model.entity.RecentFund
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface PayWithTransferRepository {

    suspend fun syncRecentFunding(
        token: String,
        body: com.bankly.core.model.data.SyncRecentFundingData,
    ): Flow<Resource<String>>

    suspend fun getRecentFunding(
        token: String,
        body: com.bankly.core.model.data.GetRecentFundingData,
    ): Flow<Resource<List<com.bankly.core.model.entity.RecentFund>>>

    suspend fun sendReceipt(
        token: String,
        body: com.bankly.core.model.data.SendReceiptData,
    ): Flow<Resource<String>>
}
