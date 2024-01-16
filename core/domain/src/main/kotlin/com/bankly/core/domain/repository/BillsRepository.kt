package com.bankly.core.domain.repository

import com.bankly.core.model.data.BillPaymentData
import com.bankly.core.model.data.ValidateCableTvNumberData
import com.bankly.core.model.data.ValidateElectricityMeterNumberData
import com.bankly.core.model.entity.BillPlan
import com.bankly.core.model.entity.BillProvider
import com.bankly.core.model.entity.CableTvNameEnquiry
import com.bankly.core.model.entity.MeterNameEnquiry
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow

interface BillsRepository {
    suspend fun performBillPayment(
        token: String,
        body: com.bankly.core.model.data.BillPaymentData,
    ): Flow<Resource<TransactionReceipt.BillPayment>>

    suspend fun performMeterNameEnquiry(
        token: String,
        body: com.bankly.core.model.data.ValidateElectricityMeterNumberData,
    ): Flow<Resource<com.bankly.core.model.entity.MeterNameEnquiry>>

    suspend fun performCableTvNameEnquiry(
        token: String,
        body: com.bankly.core.model.data.ValidateCableTvNumberData,
    ): Flow<Resource<com.bankly.core.model.entity.CableTvNameEnquiry>>

    suspend fun getAirtimeProviders(token: String): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>>
    suspend fun getInternetDataProviders(token: String): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>>
    suspend fun getCableTvProviders(token: String): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>>
    suspend fun getElectricityProviders(token: String): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>>
    suspend fun getInternetDataPlans(token: String, billId: Long): Flow<Resource<List<com.bankly.core.model.entity.BillPlan>>>
    suspend fun getCableTvPlans(token: String, billId: Long): Flow<Resource<List<com.bankly.core.model.entity.BillPlan>>>
    suspend fun getElectricityPlans(token: String, billId: Long): Flow<Resource<List<com.bankly.core.model.entity.BillPlan>>>
}
