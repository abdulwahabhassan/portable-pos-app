package com.bankly.core.data.repository

import com.bankly.core.data.EodTransactionListData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asEodInfo
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.asSyncEod
import com.bankly.core.data.util.handleApiResponse
import com.bankly.core.data.util.handleRequest
import com.bankly.core.domain.repository.EndOfDayRepository
import com.bankly.core.entity.EodInfo
import com.bankly.core.entity.SyncEod
import com.bankly.core.network.retrofit.service.PosNotificationService
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultEndOfDayRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val posNotificationService: PosNotificationService,
) : EndOfDayRepository {
    override suspend fun getEodInfo(token: String): Flow<Resource<EodInfo>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = {
                    posNotificationService.getEodInfo(
                        token = token
                    )
                },
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asEodInfo()))
        }
    }


    override suspend fun syncEod(
        token: String,
        eodTransactionListData: EodTransactionListData
    ): Flow<Resource<SyncEod>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleRequest(
                dispatcher = ioDispatcher,
                networkMonitor = networkMonitor,
                json = json,
                apiRequest = {
                    posNotificationService.postEod(
                        token = token,
                        body = eodTransactionListData.asRequestBody()
                    )
                },
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> emit(Resource.Ready(responseResult.data.asSyncEod()))
        }
    }

}