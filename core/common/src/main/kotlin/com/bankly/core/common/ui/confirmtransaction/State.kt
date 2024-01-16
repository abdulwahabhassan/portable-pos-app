package com.bankly.core.common.ui.confirmtransaction

import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.sealed.State

data class ConfirmTransactionScreenState(
    val pin: List<String> = List(4) { "" },
    val shouldShowWarningDialog: Boolean = false,
    val confirmTransactionState: State<com.bankly.core.model.entity.Status> = State.Initial,
) {
    val isDoneButtonEnabled: Boolean
        get() = pin.all { digit: String -> digit.isNotEmpty() } && confirmTransactionState !is State.Loading && confirmTransactionState !is State.Loading
    val isKeyPadEnabled: Boolean
        get() = confirmTransactionState !is State.Loading && confirmTransactionState !is State.Loading
    val isLoading: Boolean
        get() = confirmTransactionState is State.Loading || confirmTransactionState is State.Loading
}

sealed interface ConfirmTransactionScreenOneShotState : OneShotState {
    data class GoToTransactionProcessingScreen(val transactionData: TransactionData) :
        ConfirmTransactionScreenOneShotState
}
