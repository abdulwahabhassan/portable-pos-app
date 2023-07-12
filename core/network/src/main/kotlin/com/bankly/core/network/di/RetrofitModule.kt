package com.bankly.core.network.di

import com.bankly.core.network.retrofit.BanklyApiService
import com.bankly.core.network.retrofit.model.BanklyBaseUrl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providesBanklyIdentityApiService(
        client: OkHttpClient,
        json: Json
    ): BanklyApiService.Identity {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Identity.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(BanklyApiService.Identity::class.java)
    }

    @Singleton
    @Provides
    fun providesBanklyWalletApiService(
        client: OkHttpClient,
        json: Json
    ): BanklyApiService.Wallet {
        return Retrofit.Builder()
            .baseUrl(BanklyBaseUrl.Wallet.value)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(BanklyApiService.Wallet::class.java)
    }
}