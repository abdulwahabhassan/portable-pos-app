package com.bankly.core.domain.usecase

import com.bankly.core.model.data.AddDeviceTokenData
import com.bankly.core.domain.repository.PushNotificationRepository
import com.bankly.core.model.entity.AddDeviceToken
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddDeviceToFirebaseUseCase @Inject constructor(
    private val pushNotificationRepository: PushNotificationRepository,
) {
    suspend operator fun invoke(
        token: String,
        body: com.bankly.core.model.data.AddDeviceTokenData,
    ): Flow<Resource<com.bankly.core.model.entity.AddDeviceToken>> =
        pushNotificationRepository.addDeviceToFirebase(token, body)
}