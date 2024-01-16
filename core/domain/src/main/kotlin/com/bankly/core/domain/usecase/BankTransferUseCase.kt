package com.bankly.core.domain.usecase

import com.bankly.core.model.data.BankTransferData
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BankTransferUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
) {
    suspend fun invoke(
        token: String,
        body: com.bankly.core.model.data.BankTransferData,
    ): Flow<Resource<TransactionReceipt.BankTransfer>> =
        when (body) {
            is com.bankly.core.model.data.BankTransferData.AccountNumber -> {
                transferRepository.performTransferToAccountNumber(token = token, body = body)
            }

            is com.bankly.core.model.data.BankTransferData.PhoneNumber -> {
                transferRepository.performPhoneNumberTransfer(token = token, body = body)
            }
        }
}
