package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.result.AgentAccountResult
import retrofit2.http.GET
import retrofit2.http.Header

interface WalletService {
    @GET(value = "get/account/agent/default")
    suspend fun getAgentAccount(
        @Header("Authorization") token: String,
    ): ApiResponse<AgentAccountResult>
}
