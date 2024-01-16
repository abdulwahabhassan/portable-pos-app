package com.bankly.core.domain.usecase

import com.bankly.core.model.data.ResetPassCodeData
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.entity.Message
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetPassCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: com.bankly.core.model.data.ResetPassCodeData): Flow<Resource<com.bankly.core.model.entity.Message>> =
        userRepository.resetPassCode(body)
}
