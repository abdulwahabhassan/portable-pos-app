package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.result.BankNetworkResult
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkCheckerService {
    @GET("Transaction/network-checker")
    suspend fun getBankNetworkList(
        @Header("Authorization") token: String,
    ): List<BankNetworkResult>
}