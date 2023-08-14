package com.bankly.core.domain.usecase

import com.bankly.core.common.model.Resource
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.model.Bank
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBanksUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend operator fun invoke(token: String): Flow<Resource<List<Bank>>> =
        transferRepository.getBanks(token = token)
}

