package com.bankly.core.domain.repository

import com.bankly.core.model.entity.BankNetwork
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow

interface NetworkCheckerRepository {
    suspend fun getBankNetworks(token: String): Flow<Resource<List<com.bankly.core.model.entity.BankNetwork>>>
}
