package com.bankly.feature.sendmoney.ui.confirmtransaction

sealed interface ConfirmTransactionScreenEvent {
    object OnCloseClick : ConfirmTransactionScreenEvent
    data class OnDoneClick(val pin: String) : ConfirmTransactionScreenEvent
    data class OnEnterPin(val pin: List<String>) : ConfirmTransactionScreenEvent
    object OnExit : ConfirmTransactionScreenEvent
    object OnDismissWarningDialog: ConfirmTransactionScreenEvent
}