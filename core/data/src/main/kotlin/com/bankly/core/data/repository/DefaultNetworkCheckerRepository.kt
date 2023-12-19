package com.bankly.core.data.repository

import android.content.Context
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asBankNetwork
import com.bankly.core.data.util.handleNetworkCheckerApiResponse
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.loadJsonFromAsset
import com.bankly.core.domain.repository.NetworkCheckerRepository
import com.bankly.core.entity.BankLogo
import com.bankly.core.entity.BankNetwork
import com.bankly.core.network.model.result.BankNetworkResult
import com.bankly.core.network.retrofit.service.NetworkCheckerService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultNetworkCheckerRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val networkCheckerService: NetworkCheckerService,
    @ApplicationContext private val context: Context,
) : NetworkCheckerRepository {

    override suspend fun getBankNetworks(token: String): Flow<Resource<List<BankNetwork>>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleNetworkCheckerApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        networkCheckerService.getBankNetworkList(
                            token = token,
                        )
                    },
                ),
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> {
                val bankLogos = loadJsonFromAsset<List<BankLogo>>(
                    context,
                    "bank-logos.json",
                    json,
                )
                emit(
                    Resource.Ready(
                        responseResult.data.map { bankNetworkResult: BankNetworkResult ->
                            bankNetworkResult.asBankNetwork().copy(
                                bankIcon = bankLogos?.find { bankLogo ->
                                    bankNetworkResult.bankCode?.let { bankCode ->
                                        bankLogo.institutionCode == bankCode
                                    } ?: false
                                }?.logo ?: "",
                            )
                        },
                    ),
                )
            }
            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }
}
