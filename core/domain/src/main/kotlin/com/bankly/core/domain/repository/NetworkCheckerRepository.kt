package com.bankly.core.domain.repository

import com.bankly.core.entity.BankNetwork
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface NetworkCheckerRepository {
    suspend fun getBankNetworks(token: String): Flow<Resource<List<BankNetwork>>>
}
