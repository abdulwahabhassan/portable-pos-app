package com.bankly.feature.cardtransfer.navigation.viewmodel

import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.sealed.TransactionReceipt

data class CardTransferScreenState(
    val cardTransferData: TransactionData.CardTransfer? = null,
    val cardPaymentReceipt: TransactionReceipt.CardPayment? = null,
    val cardTransferReceipt: TransactionReceipt.CardTransfer? = null
)

sealed interface CardTransactionScreenOneShotState : OneShotState {
    data class GoToFailedTransactionRoute(
        val cardTransferReceipt: TransactionReceipt.CardTransfer?,
        val message: String
    ) : CardTransactionScreenOneShotState
    data class ProcessPayment(val transactionData: TransactionData.CardTransfer) : CardTransactionScreenOneShotState

    data class GoToTransactionSuccessfulRoute(val cardTransferReceipt: TransactionReceipt.CardTransfer) : CardTransactionScreenOneShotState
}
