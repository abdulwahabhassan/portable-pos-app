package com.bankly.core.domain.usecase

import com.bankly.core.common.model.Resource
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.ForgotPassCode
import com.bankly.core.model.Status
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ForgotPassCodeUseCase  @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ForgotPassCode): Flow<Resource<Status>> =
        userRepository.forgotPassCode(body)
}