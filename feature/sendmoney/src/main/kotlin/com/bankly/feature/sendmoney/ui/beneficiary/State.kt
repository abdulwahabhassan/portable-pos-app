package com.bankly.feature.sendmoney.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.entity.Bank
import com.bankly.core.sealed.State
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.feature.sendmoney.model.SavedBeneficiary

internal data class BeneficiaryScreenState(
    val accountNumberType: AccountNumberType = AccountNumberType.ACCOUNT_NUMBER,
    val isTypeError: Boolean = false,
    val typeFeedBack: String = "",
    val selectedBank: Bank? = null,
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
    val accountOrPhoneValidationState: State<AccountNameEnquiry> = State.Initial,
    val bankListState: State<List<Bank>> = State.Initial,
    val selectedTab: BeneficiaryTab = BeneficiaryTab.NEW_BENEFICIARY,
    val shouldShowSavedBeneficiaryList: Boolean = true,
    val savedBeneficiaries: List<SavedBeneficiary> = SavedBeneficiary.mockOtherBanks(),
) {
    val isContinueButtonEnabled: Boolean
        get() = accountOrPhoneTFV.text.isNotEmpty() && isAccountOrPhoneError.not() &&
            amountTFV.text.isNotEmpty() && isAmountError.not() && bankNameTFV.text.isNotEmpty() &&
            isBankNameError.not() && isNarrationError.not() &&
            accountOrPhoneValidationState !is State.Loading &&
            accountOrPhoneValidationState !is State.Error && bankListState !is State.Loading
    val isUserInputEnabled: Boolean
        get() = accountOrPhoneValidationState !is State.Loading && bankListState !is State.Loading
    val accountNumberTypeTFV: TextFieldValue
        get() = when (accountNumberType) {
            AccountNumberType.PHONE_NUMBER -> TextFieldValue(text = accountNumberType.title)
            AccountNumberType.ACCOUNT_NUMBER -> TextFieldValue(text = accountNumberType.title)
        }
    val bankNameTFV: TextFieldValue
        get() = if (selectedBank != null) {
            TextFieldValue(text = selectedBank.name)
        } else {
            TextFieldValue(
                text = "",
            )
        }
    val validationStatusIcon: Int?
        get() = when (accountOrPhoneValidationState) {
            is State.Initial -> null
            is State.Loading -> BanklyIcons.ValidationInProgress
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
}

internal sealed interface BeneficiaryScreenOneShotState : OneShotState {
    data class GoToConfirmTransactionScreen(val transactionData: TransactionData) :
        BeneficiaryScreenOneShotState
    object OnSessionExpired : BeneficiaryScreenOneShotState
}
