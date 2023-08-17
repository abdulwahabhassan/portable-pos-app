package com.bankly.feature.sendmoney.ui.confirmtransaction

import com.bankly.core.sealed.State
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.Status
import com.bankly.core.common.model.TransactionData

internal data class ConfirmTransactionScreenState(
    val pin: List<String> = List(4) { "" },
    val shouldShowWarningDialog: Boolean = false,
    val confirmTransactionState: State<Status> = State.Initial,
) {
    val isDoneButtonEnabled: Boolean
        get() = pin.all { digit: String -> digit.isNotEmpty() } && confirmTransactionState !is State.Loading && confirmTransactionState !is State.Loading
    val isKeyPadEnabled: Boolean
        get() = confirmTransactionState !is State.Loading && confirmTransactionState !is State.Loading
    val isLoading: Boolean
        get() = confirmTransactionState is State.Loading || confirmTransactionState is State.Loading
}

internal sealed interface ConfirmTransactionScreenOneShotState : OneShotState {
    data class GoToTransactionProcessingScreen(val transactionData: TransactionData): ConfirmTransactionScreenOneShotState
}