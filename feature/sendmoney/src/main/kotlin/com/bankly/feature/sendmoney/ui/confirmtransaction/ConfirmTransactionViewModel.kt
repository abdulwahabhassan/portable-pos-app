package com.bankly.feature.sendmoney.ui.confirmtransaction

import com.bankly.core.sealed.State
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.entity.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ConfirmTransactionViewModel @Inject constructor(
) : BaseViewModel<ConfirmTransactionScreenEvent, ConfirmTransactionScreenState, ConfirmTransactionScreenOneShotState>(
    ConfirmTransactionScreenState()
) {
    override suspend fun handleUiEvents(event: ConfirmTransactionScreenEvent) {
        when (event) {
            is ConfirmTransactionScreenEvent.OnDoneClick -> {
                setOneShotState(
                    ConfirmTransactionScreenOneShotState.GoToTransactionProcessingScreen(
                        event.transactionData.copy(
                            pin = uiState.value.pin.joinToString("")
                        )
                    )
                )
            }

            is ConfirmTransactionScreenEvent.OnEnterPin -> {
                setUiState { copy(pin = event.pin) }
            }

            ConfirmTransactionScreenEvent.OnExit -> {
                setUiState { copy(confirmTransactionState = State.Initial) }
            }

            ConfirmTransactionScreenEvent.OnCloseClick -> {
                setUiState { copy(shouldShowWarningDialog = true) }
            }

            ConfirmTransactionScreenEvent.OnDismissWarningDialog -> {
                setUiState { copy(shouldShowWarningDialog = false) }
            }
        }
    }
}
