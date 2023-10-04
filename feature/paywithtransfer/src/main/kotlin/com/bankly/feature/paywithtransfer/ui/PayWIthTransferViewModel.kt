package com.bankly.feature.paywithtransfer.ui

import com.bankly.core.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PayWithTransferViewModel @Inject constructor() :
    BaseViewModel<PayWithTransferScreenEvent, PayWithTransferScreenState, PayWithTransferScreenOneShotState>(
        PayWithTransferScreenState(),
    ) {
    override suspend fun handleUiEvents(event: PayWithTransferScreenEvent) {
        when (event) {
            is PayWithTransferScreenEvent.OnAccountDetailsExpandButtonClick -> {
                setUiState { copy(isAccountDetailsExpanded = !event.isExpanded) }
            }
            is PayWithTransferScreenEvent.OnCreditAlertSelected -> {
                setUiState { copy(showTransferAlertDialog = true, selectedTransferAlert = event.transferAlert) }
            }

            PayWithTransferScreenEvent.CloseTransferAlertDialog -> {
                setUiState { copy(showTransferAlertDialog = false) }
            }
        }
    }

}