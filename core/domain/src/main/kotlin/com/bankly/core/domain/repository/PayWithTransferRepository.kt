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
        body: SyncRecentFundingData,
    ): Flow<Resource<String>>

    suspend fun getRemoteRecentFunds(
        token: String,
        body: GetRecentFundingData,
    ): Flow<Resource<List<RecentFund>>>

    suspend fun getLocalRecentFunds(): Flow<List<RecentFund>>

    suspend fun sendReceipt(
        token: String,
        body: SendReceiptData,
    ): Flow<Resource<String>>

    suspend fun insertRecentFund(recentFund: RecentFund)

    suspend fun getRecentFund(transactionRef: String, sessionId: String): RecentFund?
}

