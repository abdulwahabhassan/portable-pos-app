package com.bankly.core.data.repository

import com.bankly.core.model.data.AddDeviceTokenData
import com.bankly.core.data.di.IODispatcher
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.data.util.asDeviceToken
import com.bankly.core.data.util.asRequestBody
import com.bankly.core.data.util.handleApiResponse
import com.bankly.core.data.util.handleRequest
import com.bankly.core.data.util.toNotification
import com.bankly.core.data.util.toNotificationMessage
import com.bankly.core.database.dao.NotificationMessageDao
import com.bankly.core.domain.repository.PushNotificationRepository
import com.bankly.core.model.entity.AddDeviceToken
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.core.network.retrofit.service.PushNotificationService
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultPushNotificationRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor,
    private val json: Json,
    private val pushNotificationService: PushNotificationService,
    private val notificationMessageDao: NotificationMessageDao,
) : PushNotificationRepository {
    override suspend fun addDeviceToFirebase(
        token: String,
        body: com.bankly.core.model.data.AddDeviceTokenData,
    ): Flow<Resource<com.bankly.core.model.entity.AddDeviceToken>> = flow {
        emit(Resource.Loading)
        when (
            val responseResult = handleApiResponse(
                requestResult = handleRequest(
                    dispatcher = ioDispatcher,
                    networkMonitor = networkMonitor,
                    json = json,
                    apiRequest = {
                        pushNotificationService.addDeviceToFirebase(token, body.asRequestBody())
                    }
                )
            )
        ) {
            is Result.Error -> emit(Resource.Failed(responseResult.message))
            is Result.Success -> {
                emit(
                    Resource.Ready(
                        responseResult.data.asDeviceToken()
                    ),
                )
            }

            Result.SessionExpired -> emit(Resource.SessionExpired)
        }
    }

    override fun getNotificationMessages(): Flow<List<com.bankly.core.model.entity.NotificationMessage>> {
        return notificationMessageDao.getNotificationMessages()
            .map { list -> list.map { it.toNotificationMessage() } }
    }

    override suspend fun insertNotificationMessage(notificationMessage: com.bankly.core.model.entity.NotificationMessage) {
        notificationMessageDao.insertNotificationMessage(notificationMessage.toNotification())
    }

    override suspend fun getNotificationMessage(dateTime: String): com.bankly.core.model.entity.NotificationMessage? {
        return notificationMessageDao.getNotificationMessage(dateTime)?.toNotificationMessage()
    }
}