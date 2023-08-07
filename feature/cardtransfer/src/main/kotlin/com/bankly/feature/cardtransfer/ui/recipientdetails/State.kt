package com.bankly.feature.cardtransfer.ui.recipientdetails

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.State

data class EnterRecipientDetailsScreenState(
    val bankNameTFV: TextFieldValue = TextFieldValue(text = ""),
    val accountNumberTFV: TextFieldValue = TextFieldValue(text = "0428094437"),
    val amountTFV: TextFieldValue = TextFieldValue(text = "1000.99"),
    val senderPhoneNumberTFV: TextFieldValue = TextFieldValue(text = "08167039661"),
    val isAccountNumberError: Boolean = false,
    val isAmountError: Boolean = false,
    val isBankNameError: Boolean = false,
    val isSenderPhoneNumberError: Boolean = false,
    val accountNumberFeedBack: String = "",
    val amountFeedBack: String = "",
    val senderPhoneNumberFeedBack: String = "",
    val bankNameFeedBack: String = "",
    val banks: List<String> = listOf("GTB", "FCMB", "First Bank of Nigeria (FBN)"),
    val accountValidationState: State<String> = State.Initial
) {
    val isContinueButtonEnabled: Boolean
        get() = bankNameTFV.text.isNotEmpty() && accountNumberTFV.text.isNotEmpty() && amountTFV.text.isNotEmpty() && senderPhoneNumberTFV.text.isNotEmpty() &&
                isBankNameError.not() && isAccountNumberError.not() && isAmountError.not() && isSenderPhoneNumberError.not() && accountValidationState !is State.Loading
    val isUserInputEnabled: Boolean
        get() = accountValidationState !is State.Loading
}