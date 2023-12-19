package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.BankTransferRequestBody
import com.bankly.core.network.model.request.CardTransferAccountInquiryRequestBody
import com.bankly.core.network.model.request.CardTransferRequestBody
import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.result.AccountNameEnquiryResult
import com.bankly.core.network.model.result.AccountNumberTransactionResult
import com.bankly.core.network.model.result.BankResult
import com.bankly.core.network.model.result.CardTransferAccountInquiryResult
import com.bankly.core.network.model.result.CardTransferTransactionResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FundTransferService {
    @POST("post/FundTransfer/Transfer")
    suspend fun processTransferToAccountNumber(
        @Header("Authorization") token: String,
        @Body body: BankTransferRequestBody,
    ): ApiResponse<AccountNumberTransactionResult>

    @POST("get/FundTransfer/NameEnquiry/{accountNumber}/{bankId}")
    suspend fun performBankAccountNameEnquiry(
        @Header("Authorization") token: String,
        @Path("accountNumber") accountNumber: String,
        @Path("bankId") bankId: String,
    ): ApiResponse<AccountNameEnquiryResult>

    @GET("get/FundTransfer/NameEnquiry/getBanks")
    suspend fun getBanks(
        @Header("Authorization") token: String,
    ): ApiResponse<List<BankResult>>

    @POST("post/CardTransfer/Inquiry")
    suspend fun performCardTransferAccountEnquiry(
        @Header("Authorization") token: String,
        @Body body: CardTransferAccountInquiryRequestBody,
    ): ApiResponse<CardTransferAccountInquiryResult>

    @POST("post/CardTransfer/Transfer")
    suspend fun performCardTransfer(
        @Header("Authorization") token: String,
        @Body body: CardTransferRequestBody,
    ): ApiResponse<CardTransferTransactionResult>
}
