package com.bankly.core.domain.repository

import com.bankly.core.data.BillPaymentData
import com.bankly.core.data.ValidateCableTvNumberData
import com.bankly.core.data.ValidateElectricityMeterNumberData
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.entity.MeterNameEnquiry
import com.bankly.core.entity.Plan
import com.bankly.core.entity.Provider
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface BillsRepository {
    suspend fun performBillPayment(token: String, body: BillPaymentData): Flow<Resource<Any>>
    suspend fun performMeterNameEnquiry(token: String, body: ValidateElectricityMeterNumberData): Flow<Resource<MeterNameEnquiry>>
    suspend fun performCableTvNameEnquiry(token: String, body: ValidateCableTvNumberData): Flow<Resource<CableTvNameEnquiry>>
    suspend fun getAirtimeProviders(token: String): Flow<Resource<List<Provider>>>
    suspend fun getInternetDataProviders(token: String): Flow<Resource<List<Provider>>>
    suspend fun getCableTvProviders(token: String): Flow<Resource<List<Provider>>>
    suspend fun getElectricityProviders(token: String): Flow<Resource<List<Provider>>>
    suspend fun getInternetDataPlans(token: String,  billId: Long): Flow<Resource<List<Plan>>>
    suspend fun getCableTvPlans(token: String,  billId: Long): Flow<Resource<List<Plan>>>
    suspend fun getElectricityPlans(token: String,  billId: Long): Flow<Resource<List<Plan>>>
}