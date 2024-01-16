package com.bankly.core.data.repository

import com.bankly.core.model.data.BillPaymentData
import com.bankly.core.model.data.ValidateCableTvNumberData
import com.bankly.core.model.data.ValidateElectricityMeterNumberData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asBillPayment
import com.bankly.core.data.util.asCableTvNameEnquiry
import com.bankly.core.data.util.asMeterNameEnquiry
import com.bankly.core.data.util.asPlan
import com.bankly.core.data.util.asProvider
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.handleApiResponse
import com.bankly.core.data.util.handleRequest
import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.model.entity.BillPlan
import com.bankly.core.model.entity.BillProvider
import com.bankly.core.model.entity.CableTvNameEnquiry
import com.bankly.core.model.entity.MeterNameEnquiry
import com.bankly.core.network.model.result.PlanResult
import com.bankly.core.network.model.result.ProviderResult
import com.bankly.core.network.retrofit.service.BillsService
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.Result
import com.bankly.core.model.sealed.TransactionReceipt
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
        body: com.bankly.core.model.data.BillPaymentData,
    ): Flow<Resource<TransactionReceipt.BillPayment>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun performMeterNameEnquiry(
        token: String,
        body: com.bankly.core.model.data.ValidateElectricityMeterNumberData,
    ): Flow<Resource<com.bankly.core.model.entity.MeterNameEnquiry>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        billsService.validateElectricityMeterNumber(
                            token = token,
                            body = body.asRequestBody(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asMeterNameEnquiry()))
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun performCableTvNameEnquiry(
        token: String,
        body: com.bankly.core.model.data.ValidateCableTvNumberData,
    ): Flow<Resource<com.bankly.core.model.entity.CableTvNameEnquiry>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        billsService.validateCableTvNumber(
                            token = token,
                            body = body.asRequestBody(),
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asCableTvNameEnquiry()))
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getAirtimeProviders(
        token: String,
    ): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getInternetDataProviders(
        token: String,
    ): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getCableTvProviders(
        token: String,
    ): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
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
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override suspend fun getElectricityProviders(
        token: String,
    ): Flow<Resource<List<com.bankly.core.model.entity.BillProvider>>> =
        flow {
            emit(Resource.Loading)
            when (
                val responseResult = handleApiResponse(
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
                Result.SessionExpired -> emit(Resource.SessionExpired)
            }
        }

    override suspend fun getInternetDataPlans(token: String, billId: Long): Flow<Resource<List<com.bankly.core.model.entity.BillPlan>>> =
        flow {
            emit(Resource.Loading)
            when (
                val responseResult = handleApiResponse(
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
                Result.SessionExpired -> emit(Resource.SessionExpired)
            }
        }

    override suspend fun getCableTvPlans(token: String, billId: Long): Flow<Resource<List<com.bankly.core.model.entity.BillPlan>>> =
        flow {
            emit(Resource.Loading)
            when (
                val responseResult = handleApiResponse(
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
                Result.SessionExpired -> emit(Resource.SessionExpired)
            }
        }

    override suspend fun getElectricityPlans(token: String, billId: Long): Flow<Resource<List<com.bankly.core.model.entity.BillPlan>>> =
        flow {
            emit(Resource.Loading)
            when (
                val responseResult = handleApiResponse(
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
                Result.SessionExpired -> emit(Resource.SessionExpired)
            }
        }
}
