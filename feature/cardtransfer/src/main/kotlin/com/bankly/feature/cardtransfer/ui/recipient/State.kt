package com.bankly.feature.cardtransfer.ui.recipient

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.entity.Bank
import com.bankly.core.entity.NameEnquiry
import com.bankly.core.sealed.State

internal data class RecipientScreenState(
    val selectedBank: Bank? = null,
    val accountNumberTFV: TextFieldValue = TextFieldValue(text = ""),
    val amountTFV: TextFieldValue = TextFieldValue(text = ""),
    val senderPhoneNumberTFV: TextFieldValue = TextFieldValue(text = ""),
    val isAccountNumberError: Boolean = false,
    val isAmountError: Boolean = false,
    val isBankNameError: Boolean = false,
    val isSenderPhoneNumberError: Boolean = false,
    val accountNumberFeedBack: String = "",
    val amountFeedBack: String = "",
    val senderPhoneNumberFeedBack: String = "",
    val bankNameFeedBack: String = "",
    val accountValidationState: State<NameEnquiry> = State.Initial,
    val bankListState: State<List<Bank>> = State.Initial,
) {
    val isContinueButtonEnabled: Boolean
        get() = accountNumberTFV.text.isNotEmpty() && isAccountNumberError.not() &&
            amountTFV.text.isNotEmpty() && isAmountError.not() && bankNameTFV.text.isNotEmpty() &&
            isBankNameError.not() && isSenderPhoneNumberError.not() &&
            accountValidationState !is State.Loading && accountValidationState !is State.Error &&
            bankListState !is State.Loading
    val isUserInputEnabled: Boolean
        get() = accountValidationState !is State.Loading && bankListState !is State.Loading
    val bankNameTFV: TextFieldValue
        get() = if (selectedBank != null) {
            TextFieldValue(text = selectedBank.name)
        } else {
            TextFieldValue(
                text = "",
            )
        }
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

internal sealed interface RecipientScreenOneShotState : OneShotState {
    data class GoToSelectAccountTypeScreen(val transactionData: TransactionData) :
        RecipientScreenOneShotState
}
