package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.BankTransferRequestBody
import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.result.PhoneNumberTransactionResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransferService {
    @POST("post/transfer/intra")
    suspend fun processTransferToPhoneNumber(
        @Header("Authorization") token: String,
        @Body body: BankTransferRequestBody,
    ): ApiResponse<PhoneNumberTransactionResult>
}
