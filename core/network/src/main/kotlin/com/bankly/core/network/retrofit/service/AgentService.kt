package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.response.NetworkResponse
import com.bankly.core.network.model.result.AgentResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AgentService {
    @GET("get/Agent/findBy/{phoneNumber}")
    suspend fun performAccountNameEnquiry(
        @Header("Authorization") token: String,
        @Path("phoneNumber") phoneNumber: String,
    ): NetworkResponse<AgentResult>
}
