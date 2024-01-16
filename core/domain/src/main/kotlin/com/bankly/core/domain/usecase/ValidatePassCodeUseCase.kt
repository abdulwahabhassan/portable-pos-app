package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.entity.Token
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidatePassCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(password: String, token: String): Flow<Resource<com.bankly.core.model.entity.Token>> =
        userRepository.validatePassCode(password, token)
}
