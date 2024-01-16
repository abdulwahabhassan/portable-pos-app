package com.bankly.feature.cardtransfer.navigation.viewmodel

import com.bankly.core.common.model.TransactionData
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.AccountType

sealed interface CardTransferScreenEvent {
    data class InitCardTransferData(
        val transactionData: TransactionData.CardTransfer,
        val acctType: AccountType
    ) :
        CardTransferScreenEvent

    data class OnCardTransferFailed(
        val transactionData: TransactionData.CardTransfer,
        val cardPaymentReceipt: TransactionReceipt.CardPayment,
        val message: String
    ) : CardTransferScreenEvent

    data class OnCardTransferSuccessful(
        val transactionData: TransactionData.CardTransfer,
        val cardPaymentReceipt: TransactionReceipt.CardPayment,
        val cardTransferReceipt: TransactionReceipt.CardTransfer,
    ) : CardTransferScreenEvent
}
