package com.bankly.core.domain.usecase

import com.bankly.core.data.ForgotTerminalAccessPinData
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.Status
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotTerminalAccessPinUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ForgotTerminalAccessPinData): Flow<Resource<Status>> =
        userRepository.forgotTerminalAccessPin(body)
}
