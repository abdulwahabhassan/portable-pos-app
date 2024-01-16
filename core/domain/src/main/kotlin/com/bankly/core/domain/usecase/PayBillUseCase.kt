package com.bankly.core.domain.usecase

import com.bankly.core.model.data.BillPaymentData
import com.bankly.core.domain.repository.BillsRepository
import com.bankly.core.model.sealed.Resource
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PayBillUseCase @Inject constructor(
    private val billsRepository: BillsRepository,
) {
    suspend operator fun invoke(token: String, body: com.bankly.core.model.data.BillPaymentData): Flow<Resource<TransactionReceipt.BillPayment>> =
        billsRepository.performBillPayment(token = token, body = body)
}
