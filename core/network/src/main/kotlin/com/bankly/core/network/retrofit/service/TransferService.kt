package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.InternalTransferRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransferService {
    @POST("post/transfer/intra")
    suspend fun processInternalTransfer(
        @Header("Authorization") token: String,
        @Body body: InternalTransferRequestBody
    ): NetworkResponse<Any>
}