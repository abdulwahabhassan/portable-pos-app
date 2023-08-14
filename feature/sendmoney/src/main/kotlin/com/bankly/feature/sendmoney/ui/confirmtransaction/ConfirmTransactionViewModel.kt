package com.bankly.feature.sendmoney.ui.confirmtransaction

import com.bankly.core.common.model.State
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmTransactionViewModel @Inject constructor(
) : BaseViewModel<ConfirmTransactionScreenEvent, ConfirmTransactionScreenState, ConfirmTransactionScreenOneShotState>(
    ConfirmTransactionScreenState()
) {
    override suspend fun handleUiEvents(event: ConfirmTransactionScreenEvent) {
        when (event) {
            is ConfirmTransactionScreenEvent.OnDoneClick -> {
                validatePin(pin = event.pin)
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

    private suspend fun validatePin(pin: String) {
        setUiState { copy(confirmTransactionState = State.Success(Status(true))) }
    }

}
