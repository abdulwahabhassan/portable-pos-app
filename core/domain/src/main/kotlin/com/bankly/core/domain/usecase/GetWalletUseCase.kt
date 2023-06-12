package com.bankly.core.domain.usecase

import com.bankly.core.common.model.Resource
import com.bankly.core.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetWalletUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token: String): Flow<Resource<Any>> =
        userRepository.getWallet(token)
}
