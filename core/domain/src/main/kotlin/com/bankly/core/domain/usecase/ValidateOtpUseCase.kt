package com.bankly.core.domain.usecase

import com.bankly.core.model.data.ValidateOtpData
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.entity.Status
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateOtpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: com.bankly.core.model.data.ValidateOtpData): Flow<Resource<com.bankly.core.model.entity.Status>> =
        userRepository.validateOtp(body)
}
