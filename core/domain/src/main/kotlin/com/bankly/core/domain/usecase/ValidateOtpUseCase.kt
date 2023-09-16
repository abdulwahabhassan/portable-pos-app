package com.bankly.core.domain.usecase

import com.bankly.core.data.ValidateOtpData
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.Status
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateOtpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ValidateOtpData): Flow<Resource<Status>> =
        userRepository.validateOtp(body)
}
