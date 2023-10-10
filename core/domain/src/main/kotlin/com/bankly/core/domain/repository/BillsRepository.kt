package com.bankly.core.domain.repository

import com.bankly.core.data.BillPaymentData
import com.bankly.core.data.ValidateCableTvNumberData
import com.bankly.core.data.ValidateElectricityMeterNumberData
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.entity.MeterNameEnquiry
import com.bankly.core.entity.BillPlan
import com.bankly.core.entity.BillProvider
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow

interface BillsRepository {
    suspend fun performBillPayment(
        token: String,
        body: BillPaymentData
    ): Flow<Resource<TransactionReceipt.BillPayment>>

    suspend fun performMeterNameEnquiry(
        token: String,
        body: ValidateElectricityMeterNumberData
    ): Flow<Resource<MeterNameEnquiry>>

    suspend fun performCableTvNameEnquiry(
        token: String,
        body: ValidateCableTvNumberData
    ): Flow<Resource<CableTvNameEnquiry>>

    suspend fun getAirtimeProviders(token: String): Flow<Resource<List<BillProvider>>>
    suspend fun getInternetDataProviders(token: String): Flow<Resource<List<BillProvider>>>
    suspend fun getCableTvProviders(token: String): Flow<Resource<List<BillProvider>>>
    suspend fun getElectricityProviders(token: String): Flow<Resource<List<BillProvider>>>
    suspend fun getInternetDataPlans(token: String, billId: Long): Flow<Resource<List<BillPlan>>>
    suspend fun getCableTvPlans(token: String, billId: Long): Flow<Resource<List<BillPlan>>>
    suspend fun getElectricityPlans(token: String, billId: Long): Flow<Resource<List<BillPlan>>>
}