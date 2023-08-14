package com.bankly.feature.authentication.ui.recoverpasscode

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.State
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.Status

data class RecoverPassCodeScreenState(
    val phoneNumberTFV: TextFieldValue = TextFieldValue(text = "08167039661"),
    val isPhoneNumberError: Boolean = false,
    val phoneNumberFeedBack: String = "",
    val recoverPassCodeState: State<Status> = State.Initial
) {
    val isSendCodeButtonEnabled: Boolean
        get() = phoneNumberTFV.text.isNotEmpty() && phoneNumberTFV.text.length == 11 &&
                isPhoneNumberError.not() && recoverPassCodeState !is State.Loading
    val isUserInputEnabled: Boolean
        get() = recoverPassCodeState !is State.Loading
}

sealed interface RecoverPassCodeScreenOneShotState : OneShotState