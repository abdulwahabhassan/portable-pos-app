package com.bankly.core.domain.repository

import com.bankly.core.data.AccountNumberTransferData
import com.bankly.core.data.PhoneNumberTransferData
import com.bankly.core.entity.Bank
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.sealed.Resource
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    suspend fun performTransferToAccountNumber(token: String, body: AccountNumberTransferData): Flow<Resource<TransactionReceipt.BankTransfer>>
    suspend fun performPhoneNumberTransfer(token: String, body: PhoneNumberTransferData): Flow<Resource<TransactionReceipt.BankTransfer>>
    suspend fun performBankAccountNameEnquiry(token: String, accountNumber: String, bankId: String): Flow<Resource<AccountNameEnquiry>>
    suspend fun performBankAccountNameEnquiry(token: String, phoneNumber: String): Flow<Resource<AccountNameEnquiry>>
    suspend fun getBanks(token: String): Flow<Resource<List<Bank>>>
}
