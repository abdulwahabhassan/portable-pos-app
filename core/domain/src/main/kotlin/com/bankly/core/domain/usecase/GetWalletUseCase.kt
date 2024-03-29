package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.entity.UserWallet
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWalletUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token: String): Flow<Resource<UserWallet>> =
        userRepository.getWallet(token)
}
