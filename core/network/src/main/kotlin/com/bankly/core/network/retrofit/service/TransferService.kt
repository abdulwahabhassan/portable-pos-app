package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.PhoneNumberTransferRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.result.TransactionResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransferService {
    @POST("post/transfer/intra")
    suspend fun processTransferToPhoneNumber(
        @Header("Authorization") token: String,
        @Body body: PhoneNumberTransferRequestBody
    ): NetworkResponse<TransactionResult>
}