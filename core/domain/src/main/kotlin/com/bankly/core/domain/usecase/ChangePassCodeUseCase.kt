package com.bankly.core.domain.usecase

import com.bankly.core.common.model.Resource
import com.bankly.core.domain.repository.UserRepository
import com.bankly.core.model.ChangePassCode
import com.bankly.core.model.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ChangePassCodeUseCase  @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(body: ChangePassCode): Flow<Resource<User>> =
        userRepository.changePassCode(body)
}