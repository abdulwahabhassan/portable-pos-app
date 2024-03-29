package com.bankly.core.common.ui.confirmtransaction

import com.bankly.core.common.model.TransactionData

sealed interface ConfirmTransactionScreenEvent {
    object OnCloseClick : ConfirmTransactionScreenEvent
    data class OnDoneClick(val transactionData: TransactionData) : ConfirmTransactionScreenEvent
    data class OnEnterPin(val pin: List<String>) : ConfirmTransactionScreenEvent
    object OnExit : ConfirmTransactionScreenEvent
    object OnDismissWarningDialog : ConfirmTransactionScreenEvent
}
