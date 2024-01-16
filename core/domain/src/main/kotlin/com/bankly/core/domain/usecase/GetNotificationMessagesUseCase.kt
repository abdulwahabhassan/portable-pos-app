package com.bankly.core.domain.usecase

import com.bankly.core.model.data.AddDeviceTokenData
import com.bankly.core.domain.repository.PushNotificationRepository
import com.bankly.core.model.entity.NotificationMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationMessagesUseCase @Inject constructor(
    private val pushNotificationRepository: PushNotificationRepository,
) {
    suspend operator fun invoke(): Flow<List<NotificationMessage>> =
        pushNotificationRepository.getNotificationMessages()
}