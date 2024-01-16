package com.bankly.core.common.ui.confirmtransaction

import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.model.sealed.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmTransactionViewModel @Inject constructor() :
    BaseViewModel<ConfirmTransactionScreenEvent, ConfirmTransactionScreenState, ConfirmTransactionScreenOneShotState>(
        ConfirmTransactionScreenState(),
    ) {
    override suspend fun handleUiEvents(event: ConfirmTransactionScreenEvent) {
        when (event) {
            is ConfirmTransactionScreenEvent.OnDoneClick -> {
                val data = event.transactionData
                data.pin = uiState.value.pin.joinToString("")

                setOneShotState(
                    ConfirmTransactionScreenOneShotState.GoToTransactionProcessingScreen(data),
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
