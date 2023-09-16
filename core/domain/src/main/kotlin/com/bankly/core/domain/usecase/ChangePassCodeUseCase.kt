package com.bankly.core.domain.usecase

import com.bankly.core.data.ChangePassCodeData
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.entity.User
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePassCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ChangePassCodeData): Flow<Resource<User>> =
        userRepository.changePassCode(body)
}
