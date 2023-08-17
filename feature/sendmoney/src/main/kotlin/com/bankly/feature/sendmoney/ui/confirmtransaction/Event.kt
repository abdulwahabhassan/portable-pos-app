package com.bankly.feature.sendmoney.ui.confirmtransaction

import com.bankly.core.common.model.TransactionData

internal sealed interface ConfirmTransactionScreenEvent {
    object OnCloseClick : ConfirmTransactionScreenEvent
    data class OnDoneClick(val transactionData: TransactionData) : ConfirmTransactionScreenEvent
    data class OnEnterPin(val pin: List<String>) : ConfirmTransactionScreenEvent
    object OnExit : ConfirmTransactionScreenEvent
    object OnDismissWarningDialog: ConfirmTransactionScreenEvent
}