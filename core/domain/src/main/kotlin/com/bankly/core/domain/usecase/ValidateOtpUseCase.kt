package com.bankly.core.domain.usecase

import com.bankly.core.common.model.Resource
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.Status
import com.bankly.core.model.ValidateOtp
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ValidateOtpUseCase  @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ValidateOtp): Flow<Resource<Status>> =
        userRepository.validateOtp(body)
}