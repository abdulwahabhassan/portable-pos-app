package com.bankly.feature.sendmoney.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.sealed.State
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.entity.Bank
import com.bankly.core.entity.NameEnquiry
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.core.common.model.TransactionData
import com.bankly.feature.sendmoney.model.SavedBeneficiary
import com.bankly.core.common.model.AccountNumberType

data class BeneficiaryScreenState(
    val accountNumberTypeTFV: TextFieldValue = TextFieldValue(text = AccountNumberType.ACCOUNT_NUMBER.title),
    val isTypeError: Boolean = false,
    val typeFeedBack: String = "",
    val selectedBank: Bank? = null,
    val bankNameTFV: TextFieldValue = TextFieldValue(text = selectedBank?.name ?: ""),
    val isBankNameError: Boolean = false,
    val bankNameFeedBack: String = "",
    val accountOrPhoneTFV: TextFieldValue = TextFieldValue(text = ""),
    val isAccountOrPhoneError: Boolean = false,
    val accountOrPhoneFeedBack: String = "",
    val amountTFV: TextFieldValue = TextFieldValue(text = ""),
    val isAmountError: Boolean = false,
    val amountFeedBack: String = "",
    val narrationTFV: TextFieldValue = TextFieldValue(text = ""),
    val isNarrationError: Boolean = false,
    val narrationFeedBack: String = "",
    val saveAsBeneficiary: Boolean = false,
    val accountOrPhoneValidationState: State<NameEnquiry> = State.Initial,
    val bankListState: State<List<Bank>> = State.Initial,
    val selectedTab: BeneficiaryTab = BeneficiaryTab.NEW_BENEFICIARY,
    val shouldShowSavedBeneficiaryList: Boolean = true,
    val savedBeneficiaries: List<SavedBeneficiary> = SavedBeneficiary.mockOtherBanks(),
) {
    val isContinueButtonEnabled: Boolean
        get() = accountOrPhoneTFV.text.isNotEmpty() && amountTFV.text.isNotEmpty() &&
                isAccountOrPhoneError.not() && isAmountError.not() &&
                isNarrationError.not() && accountOrPhoneValidationState !is State.Loading &&
                accountOrPhoneValidationState !is State.Error && bankListState !is State.Loading
    val isUserInputEnabled: Boolean
        get() = accountOrPhoneValidationState !is State.Loading && bankListState !is State.Loading
    val accountNumberType: AccountNumberType
        get() = when (accountNumberTypeTFV.text) {
            AccountNumberType.PHONE_NUMBER.title -> AccountNumberType.PHONE_NUMBER
            else -> AccountNumberType.ACCOUNT_NUMBER
        }
    val validationStatusIcon: Int?
        get() = when (accountOrPhoneValidationState) {
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
        get() = bankListState is State.Loading || accountOrPhoneValidationState is State.Loading
    val nameEnquiryData: NameEnquiry?
        get() = when (accountOrPhoneValidationState) {
            is State.Success -> accountOrPhoneValidationState.data
            else -> null
        }
}

sealed interface BeneficiaryScreenOneShotState: OneShotState {
    data class GoToConfirmTransactionScreen(val transactionData: TransactionData): BeneficiaryScreenOneShotState
}