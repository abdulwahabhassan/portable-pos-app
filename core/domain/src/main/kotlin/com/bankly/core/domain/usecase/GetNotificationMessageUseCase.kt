package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.PushNotificationRepository
import com.bankly.core.model.entity.NotificationMessage
import javax.inject.Inject

class GetNotificationMessageUseCase @Inject constructor(
    private val pushNotificationRepository: PushNotificationRepository,
) {
    suspend operator fun invoke(
        id: String,
    ): com.bankly.core.model.entity.NotificationMessage? =
        pushNotificationRepository.getNotificationMessage(id)
}