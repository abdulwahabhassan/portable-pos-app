package com.bankly.core.domain.usecase

import com.bankly.core.model.data.CardTransferAccountInquiryData
import com.bankly.core.model.data.ValidateCableTvNumberData
import com.bankly.core.model.data.ValidateElectricityMeterNumberData
import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.model.entity.AccountNameEnquiry
import com.bankly.core.model.entity.CableTvNameEnquiry
import com.bankly.core.model.entity.CardTransferAccountInquiry
import com.bankly.core.model.entity.MeterNameEnquiry
import com.bankly.core.model.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NameEnquiryUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
    private val billsRepository: BillsRepository,
) {
    suspend fun performBankAccountNameEnquiry(
        token: String,
        phoneNumber: String,
    ): Flow<Resource<com.bankly.core.model.entity.AccountNameEnquiry>> =
        transferRepository.performBankAccountNameEnquiry(token = token, phoneNumber = phoneNumber)

    suspend fun performBankAccountNameEnquiry(
        token: String,
        accountNumber: String,
        bankId: String,
    ): Flow<Resource<com.bankly.core.model.entity.AccountNameEnquiry>> =
        transferRepository.performBankAccountNameEnquiry(
            token = token,
            accountNumber = accountNumber,
            bankId = bankId,
        )

    suspend fun performElectricMeterNameEnquiry(
        token: String,
        body: com.bankly.core.model.data.ValidateElectricityMeterNumberData,
    ): Flow<Resource<com.bankly.core.model.entity.MeterNameEnquiry>> = billsRepository.performMeterNameEnquiry(
        token = token,
        body = body,
    )

    suspend fun performCableTvNameEnquiry(
        token: String,
        body: com.bankly.core.model.data.ValidateCableTvNumberData,
    ): Flow<Resource<com.bankly.core.model.entity.CableTvNameEnquiry>> =
        billsRepository.performCableTvNameEnquiry(
            token = token,
            body = body,
        )

    suspend fun performCardTransferAccountInquiry(
        token: String,
        body: com.bankly.core.model.data.CardTransferAccountInquiryData,
    ): Flow<Resource<com.bankly.core.model.entity.CardTransferAccountInquiry>> = transferRepository.performCardTransferAccountInquiry(
        token = token,
        body = body,
    )
}
