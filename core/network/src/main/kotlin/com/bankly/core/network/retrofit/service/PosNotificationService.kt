package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.BillPaymentRequestBody
import com.bankly.core.network.model.request.SyncEodRequestBody
import com.bankly.core.network.model.request.SyncRecentFundingRequestBody
import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.result.EodInfoResult
import com.bankly.core.network.model.result.SyncEodResult
import com.bankly.core.network.retrofit.model.Any
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PosNotificationService {
    @GET("get/EndOfDay/GetEndofDayInfo")
    suspend fun getEodInfo(
        @Header("Authorization") token: String,
    ): EodInfoResult


    @POST("post/EndOfDay")
    suspend fun postEod(
        @Header("Authorization") token: String,
        @Body body: SyncEodRequestBody,
    ): SyncEodResult
}