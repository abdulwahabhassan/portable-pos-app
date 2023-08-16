package com.bankly.core.domain.usecase

import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.entity.NameEnquiry
import com.bankly.core.sealed.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NameEnquiryUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun performNameEnquiry(token: String, phoneNumber: String): Flow<Resource<NameEnquiry>> =
        transferRepository.performNameEnquiry(token = token, phoneNumber = phoneNumber)

    suspend fun performNameEnquiry(
        token: String,
        accountNumber: String,
        bankId: String
    ): Flow<Resource<NameEnquiry>> =
        transferRepository.performNameEnquiry(
            token = token,
            accountNumber = accountNumber,
            bankId = bankId
        )
}
