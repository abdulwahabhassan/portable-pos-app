package com.bankly.core.network.di

import com.bankly.core.network.retrofit.model.BanklyBaseUrl
import com.bankly.core.network.retrofit.service.AgentService
import com.bankly.core.network.retrofit.service.FundTransferService
import com.bankly.core.network.retrofit.service.IdentityService
import com.bankly.core.network.retrofit.service.TransferService
import com.bankly.core.network.retrofit.service.WalletService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providesIdentityService(
        client: OkHttpClient,
        json: Json,
    ): IdentityService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Identity.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(IdentityService::class.java)
    }

    @Singleton
    @Provides
    fun providesWalletService(
        client: OkHttpClient,
        json: Json,
    ): WalletService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Wallet.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(WalletService::class.java)
    }

    @Singleton
    @Provides
    fun providesFundTransferService(
        client: OkHttpClient,
        json: Json,
    ): FundTransferService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.FundTransfer.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(FundTransferService::class.java)
    }

    @Singleton
    @Provides
    fun providesTransferService(
        client: OkHttpClient,
        json: Json,
    ): TransferService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Transfer.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(TransferService::class.java)
    }

    @Singleton
    @Provides
    fun providesAgentService(
        client: OkHttpClient,
        json: Json,
    ): AgentService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Agent.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(AgentService::class.java)
    }
}
