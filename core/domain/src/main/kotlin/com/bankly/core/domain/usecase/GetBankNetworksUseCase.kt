package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.NetworkCheckerRepository
import com.bankly.core.entity.BankNetwork
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBankNetworksUseCase @Inject constructor(
    private val networkCheckerRepository: NetworkCheckerRepository,
) {
    suspend operator fun invoke(token: String): Flow<Resource<List<BankNetwork>>> =
        networkCheckerRepository.getBankNetworks(token)
}