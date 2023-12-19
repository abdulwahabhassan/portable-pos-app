package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.Token
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidatePassCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(password: String, token: String): Flow<Resource<Token>> =
        userRepository.validatePassCode(password, token)
}
