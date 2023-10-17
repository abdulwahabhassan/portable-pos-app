package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.response.TransactionResponse
import com.bankly.core.network.model.result.TransactionResult
import kotlinx.serialization.Serializable
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
    ): TransactionResponse<TransactionResult>
}

@Serializable
data class TransactionsFilter(
    val dateCreatedFrom: String,
    val dateCreatedTo: String,
    val transactionType: String,
)