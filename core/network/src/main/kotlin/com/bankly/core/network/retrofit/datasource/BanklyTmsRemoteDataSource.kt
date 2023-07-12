package com.bankly.core.network.retrofit.datasource

import com.bankly.core.network.RemoteDataSource
import com.bankly.core.network.retrofit.BanklyApiService
import com.bankly.core.network.retrofit.model.BanklyBaseUrl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class BanklyTmsRemoteDataSource @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : RemoteDataSource.BanklyTmsDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BanklyBaseUrl.Tms.value)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(BanklyApiService.Tms::class.java)


}