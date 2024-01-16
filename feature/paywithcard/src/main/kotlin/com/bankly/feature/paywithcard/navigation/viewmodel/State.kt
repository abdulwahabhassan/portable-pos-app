package com.bankly.feature.paywithcard.navigation.viewmodel

import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.sealed.TransactionReceipt

data class PayWithCardScreenState(
    val cardTransferData: TransactionData.CardTransfer? = null,
    val cardPaymentReceipt: TransactionReceipt.CardPayment? = null,
    val cardTransferReceipt: TransactionReceipt.CardTransfer? = null,
)

sealed interface PayWithCardScreenOneShotState : OneShotState {
    data class GoToFailedTransactionRoute(
        val cardTransferReceipt: TransactionReceipt.CardPayment?,
        val message: String,
    ) : PayWithCardScreenOneShotState

    object ProcessPayment : PayWithCardScreenOneShotState

    data class GoToTransactionSuccessfulRoute(val cardTransferReceipt: TransactionReceipt.CardPayment) :
        PayWithCardScreenOneShotState
}
