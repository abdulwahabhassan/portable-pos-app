package com.bankly.feature.cardtransfer.ui.recipient

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.entity.Bank
import com.bankly.core.entity.AccountNameEnquiry
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
    private val isNameInquiryLoading: Boolean = false,
    private val isAccountValidationLoading: Boolean = false,
    val isBankListLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val validationIcon: Int? = null,
    val banks: List<Bank> = emptyList()
) {
    val isContinueButtonEnabled: Boolean
        get() = accountNumberTFV.text.isNotEmpty() && isAccountNumberError.not() &&
            amountTFV.text.isNotEmpty() && isAmountError.not() && bankNameTFV.text.isNotEmpty() &&
            isBankNameError.not() && isSenderPhoneNumberError.not() &&
                isNameInquiryLoading.not() && isAccountValidationLoading.not() && isBankListLoading.not()
    val isUserInputEnabled: Boolean
        get() = isNameInquiryLoading.not() && isAccountValidationLoading.not() && isBankListLoading.not()
    val bankNameTFV: TextFieldValue
        get() = if (selectedBank != null) {
            TextFieldValue(text = selectedBank.name)
        } else {
            TextFieldValue(
                text = "",
            )
        }
    val shouldShowLoadingIndicator: Boolean
        get() = isNameInquiryLoading || isAccountValidationLoading || isBankListLoading
}

internal sealed interface RecipientScreenOneShotState : OneShotState {
    data class GoToSelectAccountTypeScreen(val transactionData: TransactionData) :
        RecipientScreenOneShotState
}
