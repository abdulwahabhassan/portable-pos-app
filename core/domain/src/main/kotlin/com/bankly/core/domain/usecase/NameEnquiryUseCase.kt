package com.bankly.core.domain.usecase

import com.bankly.core.data.ValidateCableTvNumberData
import com.bankly.core.data.ValidateElectricityMeterNumberData
import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.domain.repository.TransferRepository
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.entity.MeterNameEnquiry
import com.bankly.core.sealed.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NameEnquiryUseCase @Inject constructor(
    private val transferRepository: TransferRepository,
    private val billsRepository: BillsRepository
) {
    suspend fun performBankAccountNameEnquiry(token: String, phoneNumber: String): Flow<Resource<AccountNameEnquiry>> =
        transferRepository.performBankAccountNameEnquiry(token = token, phoneNumber = phoneNumber)

    suspend fun performBankAccountNameEnquiry(
        token: String,
        accountNumber: String,
        bankId: String,
    ): Flow<Resource<AccountNameEnquiry>> =
        transferRepository.performBankAccountNameEnquiry(
            token = token,
            accountNumber = accountNumber,
            bankId = bankId,
        )

    suspend fun performElectricMeterNameEnquiry(
        token: String,
        body: ValidateElectricityMeterNumberData,
    ): Flow<Resource<MeterNameEnquiry>> = billsRepository.performMeterNameEnquiry(
            token = token,
            body = body,
        )

    suspend fun performCableTvNameEnquiry(
        token: String,
        body: ValidateCableTvNumberData,
    ): Flow<Resource<CableTvNameEnquiry>> =
        billsRepository.performCableTvNameEnquiry(
            token = token,
            body =  body
        )
}
