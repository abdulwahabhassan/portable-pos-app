package com.bankly.feature.paywithtransfer.ui

import com.bankly.feature.paywithtransfer.model.TransferAlert

internal sealed interface PayWithTransferScreenEvent {
    object CloseTransferAlertDialog : PayWithTransferScreenEvent
    data class OnCreditAlertSelected (val transferAlert: TransferAlert) : PayWithTransferScreenEvent
    data class OnAccountDetailsExpandButtonClick(val isExpanded: Boolean) : PayWithTransferScreenEvent
}
