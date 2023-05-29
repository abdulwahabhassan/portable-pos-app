package com.bankly.core.domain.usecase

import com.bankly.core.common.model.Resource
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.Message
import com.bankly.core.model.ResetPassCode
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ResetPassCodeUseCase  @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ResetPassCode): Flow<Resource<Message>> =
        userRepository.resetPassCode(body)
}