package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.GetRecentFundingRequestBody
import com.bankly.core.network.model.request.SendReceiptRequestBody
import com.bankly.core.network.model.request.SyncRecentFundingRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.result.RecentFundResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface PayWithTransferService {

    @PUT("update-as-synced")
    suspend fun syncRecentFunding(
        @Header("Authorization") token: String,
        @Body body: SyncRecentFundingRequestBody
    ): NetworkResponse<String>

    @POST("recent-fundings")
    suspend fun getRecentFunding(
        @Header("Authorization") token: String,
        @Body body: GetRecentFundingRequestBody
    ): NetworkResponse<List<RecentFundResult>>

    @POST("send-receipt")
    suspend fun sendReceipt(
        @Header("Authorization") token: String,
        @Body body: SendReceiptRequestBody
    ): NetworkResponse<String>
}