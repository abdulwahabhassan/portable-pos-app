package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.PushNotificationRepository
import com.bankly.core.model.entity.NotificationMessage
import javax.inject.Inject

class InsertNotificationMessageUseCase @Inject constructor(
    private val pushNotificationRepository: PushNotificationRepository,
) {
    suspend operator fun invoke(
        notificationMessage: com.bankly.core.model.entity.NotificationMessage,
    ): Unit =
        pushNotificationRepository.insertNotificationMessage(notificationMessage)
}