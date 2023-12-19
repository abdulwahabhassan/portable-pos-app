package com.bankly.core.common.ui.processtransaction

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.sealed.State
import com.bankly.core.sealed.TransactionReceipt

data class ProcessTransactionScreenState(
    val processingTransactionState: State<String> = State.Initial,
)

sealed interface ProcessTransactionScreenOneShotState : OneShotState {
    data class GoToTransactionFailedScreen(val message: String) : ProcessTransactionScreenOneShotState
    data class GoToTransactionSuccessScreen(val transactionReceipt: TransactionReceipt) :
        ProcessTransactionScreenOneShotState
    object OnSessionExpired : ProcessTransactionScreenOneShotState
}
