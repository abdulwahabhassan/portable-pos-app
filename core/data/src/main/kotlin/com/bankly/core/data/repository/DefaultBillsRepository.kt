package com.bankly.core.data.repository

import com.bankly.core.data.BillPaymentData
import com.bankly.core.data.ValidateCableTvNumberData
import com.bankly.core.data.ValidateElectricityMeterNumberData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asBillPayment
import com.bankly.core.data.util.asCableTvNameEnquiry
import com.bankly.core.data.util.asMeterNameEnquiry
import com.bankly.core.data.util.asPlan
import com.bankly.core.data.util.asProvider
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.handleResponse
import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.entity.MeterNameEnquiry
import com.bankly.core.entity.BillPlan
import com.bankly.core.entity.BillProvider
import com.bankly.core.network.model.result.PlanResult
import com.bankly.core.network.model.result.ProviderResult
import com.bankly.core.network.retrofit.service.BillsService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultBillsRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val billsService: BillsService,
) : BillsRepository {
    override suspend fun performBillPayment(
        token: String,
        body: BillPaymentData
    ): Flow<Resource<TransactionReceipt.BillPayment>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        billsService.processBillPayment(
                            token = token,
                            body = body.asRequestBody(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asBillPayment()))
        }
    }

    override suspend fun performMeterNameEnquiry(
        token: String,
        body: ValidateElectricityMeterNumberData
    ): Flow<Resource<MeterNameEnquiry>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        billsService.validateElectricityMeterNumber(
                            token = token,
                            body = body.asRequestBody()
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asMeterNameEnquiry()))
        }
    }

    override suspend fun performCableTvNameEnquiry(
        token: String,
        body: ValidateCableTvNumberData
    ): Flow<Resource<CableTvNameEnquiry>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        billsService.validateCableTvNumber(
                            token = token,
                            body = body.asRequestBody()
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asCableTvNameEnquiry()))
        }
    }

    override suspend fun getAirtimeProviders(
        token: String
    ): Flow<Resource<List<BillProvider>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { billsService.getAirtimeProviders(token = token) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { providerResult: ProviderResult -> providerResult.asProvider() }))
        }
    }

    override suspend fun getInternetDataProviders(
        token: String
    ): Flow<Resource<List<BillProvider>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { billsService.getInternetDataProviders(token = token) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { providerResult: ProviderResult -> providerResult.asProvider() }))
        }
    }

    override suspend fun getCableTvProviders(
        token: String
    ): Flow<Resource<List<BillProvider>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { billsService.getCableTvProviders(token = token) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { providerResult: ProviderResult -> providerResult.asProvider() }))
        }
    }

    override suspend fun getElectricityProviders(
        token: String
    ): Flow<Resource<List<BillProvider>>> =
        flow {
            emit(Resource.Loading)
            when (
                val responseResult = handleResponse(
                    requestResult = handleRequest(
                        dispatcher = ioDispatcher,
                        networkMonitor = networkMonitor,
                        json = json,
                        apiRequest = { billsService.getElectricityProviders(token = token) },
                    ),
                )
            ) {
                is Result.Error -> emit(Resource.Failed(responseResult.message))
                is Result.Success -> emit(Resource.Ready(responseResult.data.map { providerResult: ProviderResult -> providerResult.asProvider() }))
            }
        }

    override suspend fun getInternetDataPlans(token: String, billId: Long): Flow<Resource<List<BillPlan>>> =
    flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { billsService.getInternetDataPlans(token = token, billId = billId) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { planResult: PlanResult -> planResult.asPlan() }))
        }
    }

    override suspend fun getCableTvPlans(token: String, billId: Long): Flow<Resource<List<BillPlan>>> =
    flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { billsService.getCableTvPlans(token = token, billId = billId) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { planResult: PlanResult -> planResult.asPlan() }))
        }
    }

    override suspend fun getElectricityPlans(token: String, billId: Long): Flow<Resource<List<BillPlan>>> =
    flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = { billsService.getElectricityPlans(token = token, billId = billId) },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.map { planResult: PlanResult -> planResult.asPlan() }))
        }
    }

}