package com.bankly.feature.authentication.ui.setnewpasscode

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.entity.Message
import com.bankly.core.model.sealed.State

data class SetNewPassCodeScreenState(
    val passCodeTFV: TextFieldValue = TextFieldValue(text = ""),
    val confirmPassCodeTFV: TextFieldValue = TextFieldValue(text = ""),
    val isPassCodeError: Boolean = false,
    val isConfirmPassCodeError: Boolean = false,
    val passCodeFeedBack: String = "",
    val confirmPassCodeFeedBack: String = "",
    val shouldShowWarningDialog: Boolean = false,
    val setNewPassCodeState: State<com.bankly.core.model.entity.Message> = State.Initial,
) {
    val isDoneButtonEnabled: Boolean
        get() = passCodeTFV.text.isNotEmpty() && confirmPassCodeTFV.text.isNotEmpty() &&
            isPassCodeError.not() && isConfirmPassCodeError.not() && setNewPassCodeState !is State.Loading
    val isUserInputEnabled: Boolean
        get() = setNewPassCodeState !is State.Loading
}

sealed interface SetNewPassCodeScreenOneShotState : OneShotState
