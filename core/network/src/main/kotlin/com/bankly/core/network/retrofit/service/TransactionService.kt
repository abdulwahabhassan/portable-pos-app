package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.response.TransactionApiResponse
import com.bankly.core.network.model.result.TransactionFilterTypeResult
import com.bankly.core.network.model.result.TransactionResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TransactionService {
    @GET(value = "get/transaction/agent/count/{min}/{max}/{filter}")
    suspend fun getTransactions(
        @Header("Authorization") token: String,
        @Path("min") minimum: Long,
        @Path("max") maximum: Long,
        @Path("filter") filter: String
    ): TransactionApiResponse<TransactionResult>

    @GET(value = "get/transaction/agenttransactionTypes")
    suspend fun getTransactionFilterTypes(
        @Header("Authorization") token: String,
    ): List<TransactionFilterTypeResult>
}
