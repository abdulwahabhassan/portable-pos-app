package com.bankly.feature.sendmoney.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.State
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.model.Bank
import com.bankly.core.model.NameEnquiry
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.feature.sendmoney.model.ConfirmTransactionDetails
import com.bankly.feature.sendmoney.model.SavedBeneficiary
import com.bankly.feature.sendmoney.model.Type

data class BeneficiaryScreenState(
    val typeTFV: TextFieldValue = TextFieldValue(text = Type.ACCOUNT_NUMBER.title),
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
    val type: Type
        get() = when (typeTFV.text) {
            Type.PHONE_NUMBER.title -> Type.PHONE_NUMBER
            else -> Type.ACCOUNT_NUMBER
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
    data class GoToConfirmTransactionScreen(val confirmTransactionDetails: ConfirmTransactionDetails): BeneficiaryScreenOneShotState
}