package com.bankly.core.domain.repository


import com.bankly.core.model.data.AddDeviceTokenData
import com.bankly.core.model.entity.AddDeviceToken
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface PushNotificationRepository {

    suspend fun addDeviceToFirebase(
        token: String,
        body: com.bankly.core.model.data.AddDeviceTokenData,
    ): Flow<Resource<com.bankly.core.model.entity.AddDeviceToken>>

    fun getNotificationMessages(): Flow<List<com.bankly.core.model.entity.NotificationMessage>>

    suspend fun insertNotificationMessage(notificationMessage: com.bankly.core.model.entity.NotificationMessage)

    suspend fun getNotificationMessage(id: String): com.bankly.core.model.entity.NotificationMessage?
}