package com.bankly.feature.paywithtransfer.ui.sendreceipt

import androidx.compose.ui.text.input.TextFieldValue

sealed interface SendReceiptScreenEvent {
    data class OnContinueClick(val phoneNumber: String) : SendReceiptScreenEvent
    data class OnEnterPhoneNumber(val phoneNumberTFV: TextFieldValue) : SendReceiptScreenEvent
}
