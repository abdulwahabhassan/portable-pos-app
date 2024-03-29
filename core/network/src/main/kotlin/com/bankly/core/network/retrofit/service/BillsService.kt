package com.bankly.core.network.retrofit.service

import com.bankly.core.network.model.request.BillPaymentRequestBody
import com.bankly.core.network.model.request.ValidateCableTvNumberRequestBody
import com.bankly.core.network.model.request.ValidateElectricityMeterNumberRequestBody
import com.bankly.core.network.model.response.ApiResponse
import com.bankly.core.network.model.result.BillPaymentResult
import com.bankly.core.network.model.result.CableTvNameEnquiryResult
import com.bankly.core.network.model.result.MeterNameEnquiryResult
import com.bankly.core.network.model.result.PlanResult
import com.bankly.core.network.model.result.ProviderResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BillsService {
    @GET("get/bill/getAirtimeBills")
    suspend fun getAirtimeProviders(
        @Header("Authorization") token: String,
    ): ApiResponse<List<ProviderResult>>

    @GET("get/bill/getDataBills")
    suspend fun getInternetDataProviders(
        @Header("Authorization") token: String,
    ): ApiResponse<List<ProviderResult>>

    @GET("get/Bill/GetDataBillItems/{billId}")
    suspend fun getInternetDataPlans(
        @Header("Authorization") token: String,
        @Path("billId") billId: Long,
    ): ApiResponse<List<PlanResult>>

    @GET("get/bill/getCableBills/")
    suspend fun getCableTvProviders(
        @Header("Authorization") token: String,
    ): ApiResponse<List<ProviderResult>>

    @GET("get/Bill/GetCableBillItems/{billId}")
    suspend fun getCableTvPlans(
        @Header("Authorization") token: String,
        @Path("billId") billId: Long,
    ): ApiResponse<List<PlanResult>>

    @POST("post/bill/validate/cableTv")
    suspend fun validateCableTvNumber(
        @Header("Authorization") token: String,
        @Body body: ValidateCableTvNumberRequestBody,
    ): ApiResponse<CableTvNameEnquiryResult>

    @GET("get/bill/getElectricityBills")
    suspend fun getElectricityProviders(
        @Header("Authorization") token: String,
    ): ApiResponse<List<ProviderResult>>

    @GET("get/Bill/GetElectricityBillItems/{billId}")
    suspend fun getElectricityPlans(
        @Header("Authorization") token: String,
        @Path("billId") billId: Long,
    ): ApiResponse<List<PlanResult>>

    @POST("post/bill/validate/Electricity")
    suspend fun validateElectricityMeterNumber(
        @Header("Authorization") token: String,
        @Body body: ValidateElectricityMeterNumberRequestBody,
    ): ApiResponse<MeterNameEnquiryResult>

    @POST("post/Bill/payment")
    suspend fun processBillPayment(
        @Header("Authorization") token: String,
        @Body body: BillPaymentRequestBody,
    ): ApiResponse<BillPaymentResult>
}
