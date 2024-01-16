package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.AddDeviceTokenRequestBody
import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.result.AddDeviceTokenResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PushNotificationService {

    @POST("notification/user-device/add-device-to-firebase")
    suspend fun addDeviceToFirebase(
        @Header("Authorization") token: String,
        @Body addDeviceTokenRequestBody: AddDeviceTokenRequestBody
    ): ApiResponse<AddDeviceTokenResult>
}