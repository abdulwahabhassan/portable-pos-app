package com.bankly.core.domain.repository

import com.bankly.core.model.data.BankTransferData
import com.bankly.core.model.data.CardTransferAccountInquiryData
import com.bankly.core.model.data.CardTransferData
import com.bankly.core.model.entity.AccountNameEnquiry
import com.bankly.core.model.entity.Bank
import com.bankly.core.model.entity.CardTransferAccountInquiry
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    suspend fun performTransferToAccountNumber(
        token: String,
        body: com.bankly.core.model.data.BankTransferData,
    ): Flow<Resource<TransactionReceipt.BankTransfer>>

    suspend fun performPhoneNumberTransfer(
        token: String,
        body: com.bankly.core.model.data.BankTransferData,
    ): Flow<Resource<TransactionReceipt.BankTransfer>>

    suspend fun performBankAccountNameEnquiry(
        token: String,
        accountNumber: String,
        bankId: String,
    ): Flow<Resource<com.bankly.core.model.entity.AccountNameEnquiry>>

    suspend fun performBankAccountNameEnquiry(
        token: String,
        phoneNumber: String,
    ): Flow<Resource<com.bankly.core.model.entity.AccountNameEnquiry>>

    suspend fun getBanks(token: String): Flow<Resource<List<com.bankly.core.model.entity.Bank>>>

    suspend fun performCardTransferAccountInquiry(
        token: String,
        body: com.bankly.core.model.data.CardTransferAccountInquiryData,
    ): Flow<Resource<com.bankly.core.model.entity.CardTransferAccountInquiry>>

    suspend fun performCardTransfer(
        token: String,
        body: com.bankly.core.model.data.CardTransferData,
    ): Flow<Resource<TransactionReceipt.CardTransfer>>
}
