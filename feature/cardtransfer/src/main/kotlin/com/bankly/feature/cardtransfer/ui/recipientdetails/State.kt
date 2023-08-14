package com.bankly.feature.cardtransfer.ui.recipientdetails

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.State
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.model.Bank

data class EnterRecipientDetailsScreenState(
    val selectedBank: Bank? = null,
    val bankNameTFV: TextFieldValue = TextFieldValue(text = selectedBank?.name ?: ""),
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
    val accountValidationState: State<String> = State.Initial,
    val bankListState: State<List<Bank>> = State.Initial,
) {
    val isContinueButtonEnabled: Boolean
        get() = bankNameTFV.text.isNotEmpty() && accountNumberTFV.text.isNotEmpty() &&
                amountTFV.text.isNotEmpty() && senderPhoneNumberTFV.text.isNotEmpty() &&
                isBankNameError.not() && isAccountNumberError.not() && isAmountError.not() &&
                isSenderPhoneNumberError.not() && accountValidationState !is State.Loading &&
                accountValidationState !is State.Error
    val isUserInputEnabled: Boolean
        get() = accountValidationState !is State.Loading && bankListState !is State.Loading
    val validationIcon: Int?
        get() = when (accountValidationState) {
            State.Initial -> null
            State.Loading -> BanklyIcons.ValidationInProgress
            is State.Error -> BanklyIcons.ValidationFailed
            is State.Success -> BanklyIcons.ValidationPassed
        }
    val banks: List<Bank>
        get() = when (bankListState) {
            is State.Success -> bankListState.data
            else -> emptyList()
        }
    val isBankListLoading: Boolean
        get() = bankListState is State.Loading
    val shouldShowLoadingIndicator: Boolean
        get() = bankListState is State.Loading || accountValidationState is State.Loading
}