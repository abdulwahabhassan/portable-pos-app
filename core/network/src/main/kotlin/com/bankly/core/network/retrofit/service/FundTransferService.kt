package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.result.BankResult
import com.bankly.core.network.model.result.NameEnquiryResult
import com.bankly.core.network.model.request.ExternalTransferRequestBody
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.result.TransactionResult
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FundTransferService {
    @POST("post/FundTransfer/Transfer")
    suspend fun processExternalTransfer(
        @Header("Authorization") token: String,
        @Body body: ExternalTransferRequestBody
    ): NetworkResponse<TransactionResult>

    @POST("get/FundTransfer/NameEnquiry/{accountNumber}/{bankId}")
    suspend fun performNameEnquiry(
        @Header("Authorization") token: String,
        @Path("accountNumber") accountNumber: String,
        @Path("bankId") bankId: String,
    ): NetworkResponse<NameEnquiryResult>

    @GET("get/FundTransfer/NameEnquiry/getBanks")
    suspend fun getBanks(
        @Header("Authorization") token: String,
    ): NetworkResponse<List<BankResult>>
}

@Serializable
data class Any(
    val nil: String,
) : kotlin.Any() {

}