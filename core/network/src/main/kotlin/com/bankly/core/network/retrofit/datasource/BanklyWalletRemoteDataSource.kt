package com.bankly.core.network.retrofit.datasource

import com.bankly.core.network.RemoteDataSource
import com.bankly.core.network.model.ResultMessage
import com.bankly.core.network.model.WalletResult
import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.retrofit.BanklyApiService
import javax.inject.Inject
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class BanklyWalletRemoteDataSource @Inject constructor(
    private val apiService: BanklyApiService.Wallet
) : RemoteDataSource.BanklyWalletDataSource {
    override suspend fun getWallet(token: String): NetworkResponse<WalletResult> {
        return apiService.getWallet(token = token)
    }
}