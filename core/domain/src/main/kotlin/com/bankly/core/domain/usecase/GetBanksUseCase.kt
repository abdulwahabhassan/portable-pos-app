package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.entity.Bank
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBanksUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend operator fun invoke(token: String): Flow<Resource<List<Bank>>> =
        transferRepository.getBanks(token = token)
}
