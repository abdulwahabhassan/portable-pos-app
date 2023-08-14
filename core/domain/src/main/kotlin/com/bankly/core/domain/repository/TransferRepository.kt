package com.bankly.core.domain.repository

import com.bankly.core.common.model.Resource
import com.bankly.core.model.Bank
import com.bankly.core.model.ExternalTransfer
import com.bankly.core.model.InternalTransfer
import com.bankly.core.model.NameEnquiry
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    suspend fun performExternalTransfer(token: String, body: ExternalTransfer): Flow<Resource<Any>>
    suspend fun performInternalTransfer(token: String, body: InternalTransfer): Flow<Resource<Any>>
    suspend fun performNameEnquiry(token: String, accountNumber: String, bankId: String): Flow<Resource<NameEnquiry>>
    suspend fun performNameEnquiry(token: String, phoneNumber: String): Flow<Resource<NameEnquiry>>
    suspend fun getBanks(token: String): Flow<Resource<List<Bank>>>
}