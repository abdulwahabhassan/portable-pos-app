package com.bankly.core.network.retrofit.datasource

import com.bankly.core.network.RemoteDataSource
import com.bankly.core.network.retrofit.BanklyApiService
import com.bankly.core.network.retrofit.model.BanklyBaseUrl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class BanklyNotificationRemoteDataSource @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : RemoteDataSource.BanklyNotificationDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BanklyBaseUrl.Notification.value)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(BanklyApiService.Notification::class.java)


}
