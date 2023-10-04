package com.bankly.feature.paywithtransfer.ui.sendreceipt

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.Status
import com.bankly.core.sealed.State

data class SendReceiptScreenState(
    val phoneNumberTFV: TextFieldValue = TextFieldValue(text = ""),
    val isPhoneNumberError: Boolean = false,
    val phoneNumberFeedBack: String = "",
    val isLoading: Boolean = false,
) {
    val isContinueButtonEnabled: Boolean
        get() = phoneNumberTFV.text.isNotEmpty() && phoneNumberTFV.text.length == 11 &&
            isPhoneNumberError.not() && isLoading.not()
    val isUserInputEnabled: Boolean
        get() = isLoading.not()
}

sealed interface SendReceiptScreenOneShotState : OneShotState {
    object GoToSuccessfulScreen : SendReceiptScreenOneShotState
}

