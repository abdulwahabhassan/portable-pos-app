package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.NameEnquiryResult
import com.bankly.core.network.model.response.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AgentService {
    @GET("agent/get/Agent/findBy/{phoneNumber}")
    suspend fun performNameEnquiry(
        @Header("Authorization") token: String,
        @Path("phoneNumber") phoneNumber: String,
    ): NetworkResponse<NameEnquiryResult>
}