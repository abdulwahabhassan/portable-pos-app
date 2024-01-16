package com.bankly.core.domain.usecase

import com.bankly.core.model.data.ChangePassCodeData
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.entity.User
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePassCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: com.bankly.core.model.data.ChangePassCodeData): Flow<Resource<User>> =
        userRepository.changePassCode(body)
}
