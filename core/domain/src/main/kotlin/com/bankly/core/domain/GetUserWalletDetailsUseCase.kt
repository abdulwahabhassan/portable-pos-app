package com.bankly.core.domain

import com.bankly.core.data.model.UserWallet
import com.bankly.core.data.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * A use case which returns the user's wallet
 */
class GetUserWalletDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<UserWallet> = userRepository.getUserWallet()
}
