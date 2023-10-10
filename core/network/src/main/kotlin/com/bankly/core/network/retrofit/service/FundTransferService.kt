package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.BankTransferRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.result.AccountNameEnquiryResult
import com.bankly.core.network.model.result.AccountNumberTransactionResult
import com.bankly.core.network.model.result.BankResult
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
    ): NetworkResponse<AccountNumberTransactionResult>

    @POST("get/FundTransfer/NameEnquiry/{accountNumber}/{bankId}")
    suspend fun performBankAccountNameEnquiry(
        @Header("Authorization") token: String,
        @Path("accountNumber") accountNumber: String,
        @Path("bankId") bankId: String,
    ): NetworkResponse<AccountNameEnquiryResult>

    @GET("get/FundTransfer/NameEnquiry/getBanks")
    suspend fun getBanks(
        @Header("Authorization") token: String,
    ): NetworkResponse<List<BankResult>>
}