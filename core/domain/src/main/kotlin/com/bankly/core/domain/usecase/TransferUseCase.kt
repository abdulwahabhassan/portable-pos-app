package com.bankly.core.domain.usecase

import com.bankly.core.data.AccountNumberTransferData
import com.bankly.core.data.PhoneNumberTransferData
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.Transaction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun performPhoneNumberTransfer(
        token: String,
        body: PhoneNumberTransferData
    ): Flow<Resource<Transaction.BankTransfer>> =
        transferRepository.performPhoneNumberTransfer(token = token, body = body)

    suspend fun performTransferToAccountNumber(
        token: String,
        body: AccountNumberTransferData
    ): Flow<Resource<Transaction.BankTransfer>> =
        transferRepository.performTransferToAccountNumber(token = token, body = body)
}
