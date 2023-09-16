package com.bankly.core.domain.usecase

import com.bankly.core.data.AccountNumberTransferData
import com.bankly.core.data.PhoneNumberTransferData
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun performPhoneNumberTransfer(
        token: String,
        body: PhoneNumberTransferData,
    ): Flow<Resource<TransactionReceipt.BankTransfer>> =
        transferRepository.performPhoneNumberTransfer(token = token, body = body)

    suspend fun performTransferToAccountNumber(
        token: String,
        body: AccountNumberTransferData,
    ): Flow<Resource<TransactionReceipt.BankTransfer>> =
        transferRepository.performTransferToAccountNumber(token = token, body = body)
}
