package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.UserWallet
import com.bankly.core.sealed.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetWalletUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token: String): Flow<Resource<UserWallet>> =
        userRepository.getWallet(token)
}
