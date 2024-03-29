package com.bankly.core.network.di

import com.bankly.core.network.retrofit.model.BanklyBaseUrl
import com.bankly.core.network.retrofit.service.AgentService
import com.bankly.core.network.retrofit.service.BillsService
import com.bankly.core.network.retrofit.service.FundTransferService
import com.bankly.core.network.retrofit.service.IdentityService
import com.bankly.core.network.retrofit.service.NetworkCheckerService
import com.bankly.core.network.retrofit.service.PayWithTransferService
import com.bankly.core.network.retrofit.service.PosNotificationService
import com.bankly.core.network.retrofit.service.PushNotificationService
import com.bankly.core.network.retrofit.service.TransactionService
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

    @Singleton
    @Provides
    fun providesBillsService(
        client: OkHttpClient,
        json: Json,
    ): BillsService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Bills.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(BillsService::class.java)
    }

    @Singleton
    @Provides
    fun providesPayWithTransferService(
        client: OkHttpClient,
        json: Json,
    ): PayWithTransferService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.PayWithTransfer.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(PayWithTransferService::class.java)
    }

    @Singleton
    @Provides
    fun providesTransactionService(
        client: OkHttpClient,
        json: Json,
    ): TransactionService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Transaction.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(TransactionService::class.java)
    }

    @Provides
    fun providesNetworkCheckerService(
        client: OkHttpClient,
        json: Json,
    ): NetworkCheckerService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.NetworkChecker.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(NetworkCheckerService::class.java)
    }

    @Provides
    fun providesPosNotificationService(
        client: OkHttpClient,
        json: Json,
    ): PosNotificationService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.PosNotification.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(PosNotificationService::class.java)
    }

    @Provides
    fun providesPushNotificationService(
        client: OkHttpClient,
        json: Json,
    ): PushNotificationService {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.PushNotification.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(PushNotificationService::class.java)
    }


}

