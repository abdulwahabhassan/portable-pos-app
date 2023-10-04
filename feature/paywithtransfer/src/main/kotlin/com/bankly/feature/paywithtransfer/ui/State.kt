package com.bankly.feature.paywithtransfer.ui

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.feature.paywithtransfer.model.TransferAlert

internal data class PayWithTransferScreenState(
    val isLoading: Boolean = false,
    val accountDetails: String = "",
    val isAccountDetailsExpanded: Boolean = false,
    val transferAlertList: List<TransferAlert> = emptyList(),
    val showTransferAlertDialog: Boolean = false,
    val selectedTransferAlert: TransferAlert? = null
)

internal sealed interface  PayWithTransferScreenOneShotState : OneShotState {
    data class ShowErrorDialog(val transferAlert: TransferAlert) : PayWithTransferScreenOneShotState
}
