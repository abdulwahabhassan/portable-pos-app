package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.Message
import com.bankly.core.data.ResetPassCodeData
import com.bankly.core.sealed.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ResetPassCodeUseCase  @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ResetPassCodeData): Flow<Resource<Message>> =
        userRepository.resetPassCode(body)
}