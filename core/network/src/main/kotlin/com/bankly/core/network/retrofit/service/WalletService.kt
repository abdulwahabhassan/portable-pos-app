package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.WalletResult
import com.bankly.core.network.model.response.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface WalletService {
    @GET(value = "get/account/agent/default")
    suspend fun getWallet(
        @Header("Authorization") token: String,
    ): NetworkResponse<WalletResult>
}