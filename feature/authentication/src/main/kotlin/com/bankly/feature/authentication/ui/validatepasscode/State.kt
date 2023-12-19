package com.bankly.feature.authentication.ui.validatepasscode

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.viewmodel.OneShotState

data class ValidatePassCodeScreenState(
    val passCodeTFV: TextFieldValue = TextFieldValue(text = "Gdz36Val"),
    val isPassCodeError: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val passCodeFeedBack: String = "",
    val isLoading: Boolean = false,
) {
    val isContinueButtonEnabled: Boolean
        get() = passCodeTFV.text.isNotEmpty() && isPassCodeError.not()
    val isUserInputEnabled: Boolean
        get() = isLoading.not()
}

sealed interface ValidatePassCodeScreenOneShotState : OneShotState {
    object GoToSettingsScreen : ValidatePassCodeScreenOneShotState
}
