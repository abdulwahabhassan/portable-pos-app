package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.Token
import com.bankly.core.sealed.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userName: String, password: String): Flow<Resource<Token>> =
        userRepository.getToken(userName, password)
}
