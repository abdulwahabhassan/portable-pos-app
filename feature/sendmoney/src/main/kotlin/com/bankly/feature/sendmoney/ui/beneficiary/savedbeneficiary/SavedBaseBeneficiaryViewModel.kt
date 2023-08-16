package com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary

import com.bankly.core.common.util.DecimalFormatter
import com.bankly.core.common.util.Validator
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.model.TransactionType
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryScreenEvent
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryScreenOneShotState
import com.bankly.feature.sendmoney.ui.beneficiary.BaseBeneficiaryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedBaseBeneficiaryViewModel @Inject constructor(
    nameEnquiryUseCase: NameEnquiryUseCase,
    getBanksUseCase: GetBanksUseCase,
    userPreferencesDataStore: UserPreferencesDataStore
) : BaseBeneficiaryViewModel(
    nameEnquiryUseCase, getBanksUseCase, userPreferencesDataStore
) {

    override suspend fun handleUiEvents(event: BeneficiaryScreenEvent) {
        when (event) {
            is BeneficiaryScreenEvent.OnEnterAccountOrPhoneNumber -> {

                val isEmpty = event.accountOrPhoneNumberTFV.text.trim().isEmpty()
                val isValid =
                    if (uiState.value.accountNumberType == AccountNumberType.PHONE_NUMBER) Validator.isPhoneNumberValid(event.accountOrPhoneNumberTFV.text.trim())
                    else Validator.isAccountNumberValid(event.accountOrPhoneNumberTFV.text.trim())

                setUiState {
                    copy(
                        accountOrPhoneTFV = event.accountOrPhoneNumberTFV,
                        isAccountOrPhoneError = isEmpty || isValid.not(),
                        accountOrPhoneFeedBack = if (isEmpty) "Please enter ${if (uiState.value.accountNumberType == AccountNumberType.PHONE_NUMBER) "phone" else "account"} number"
                        else if (isValid.not()) "Please enter a valid ${if (uiState.value.accountNumberType == AccountNumberType.PHONE_NUMBER) "phone" else "account"} number"
                        else ""
                    )
                }

                if (isValid && isEmpty.not()) {
                    doNameEnquiry(
                        number = event.accountOrPhoneNumberTFV.text,
                        bankId = uiState.value.selectedBank?.id,
                        channel = event.sendMoneyChannel
                    )
                }
            }

            is BeneficiaryScreenEvent.OnEnterAmount -> {

                val cleanedUpAmount = DecimalFormatter().cleanup(event.amountTFV.text)
                val isEmpty = cleanedUpAmount.isEmpty()
                val isValid = if (isEmpty) false else Validator.isAmountValid(
                    cleanedUpAmount.replace(",", "").toDouble()
                )

                val amountFeedBack = if (isEmpty) "Please enter amount"
                else if (isValid.not()) "Please enter a valid amount"
                else ""

                setUiState {
                    copy(
                        amountTFV = event.amountTFV.copy(cleanedUpAmount),
                        isAmountError = isEmpty || isValid.not(),
                        amountFeedBack = amountFeedBack
                    )
                }
            }

            is BeneficiaryScreenEvent.OnSelectBank -> {
                setUiState {
                    copy(
                        bankNameTFV = bankNameTFV.copy(text = event.bank.name),
                        selectedBank = event.bank
                    )
                }
                validateAccountNumber(
                    accountNumber = uiState.value.accountOrPhoneTFV.text,
                    bankId = event.bank.id
                )
            }

            is BeneficiaryScreenEvent.OnTypeSelected -> {
                setUiState { copy(accountNumberTypeTFV = event.typeTFV) }
            }

            is BeneficiaryScreenEvent.OnEnterNarration -> {
                setUiState { copy(narrationTFV = event.narrationTFV) }
            }

            is BeneficiaryScreenEvent.OnToggleSaveAsBeneficiary -> {
                setUiState { copy(saveAsBeneficiary = event.toggleState) }
            }

            is BeneficiaryScreenEvent.OnTabSelected -> {
                setUiState {
                    copy(selectedTab = event.tab)
                }
            }

            is BeneficiaryScreenEvent.OnBeneficiarySelected -> {
                setUiState {
                    copy(
                        accountOrPhoneTFV = accountOrPhoneTFV.copy(text = event.savedBeneficiary.accountNumber),
                        bankNameTFV = bankNameTFV.copy(text = event.savedBeneficiary.bankName),
                        shouldShowSavedBeneficiaryList = false
                    )
                }
                validateAccountNumber(
                    accountNumber = event.savedBeneficiary.accountNumber,
                    bankId = event.savedBeneficiary.bankId,
                )
            }

            is BeneficiaryScreenEvent.OnChangeSelectedSavedBeneficiary -> {
                setUiState { copy(shouldShowSavedBeneficiaryList = true) }
            }

            is BeneficiaryScreenEvent.OnContinueClick -> {

                val currentState = uiState.value
                val amount =
                    DecimalFormatter().cleanup(currentState.amountTFV.text).replace(",", "")
                        .toDouble()

                currentState.nameEnquiryData?.let { nameEnquiry ->
                    setOneShotState(
                        BeneficiaryScreenOneShotState.GoToConfirmTransactionScreen(
                            TransactionData(
                                when (event.sendMoneyChannel) {
                                    SendMoneyChannel.BANKLY_TO_BANKLY -> TransactionType.BANK_TRANSFER_INTERNAL
                                    SendMoneyChannel.BANKLY_TO_OTHER -> TransactionType.BANK_TRANSFER_EXTERNAL
                                },
                                nameEnquiry.accountNumber,
                                nameEnquiry.accountName,
                                amount,
                                0.00,
                                0.00,
                                nameEnquiry.bankName,
                                currentState.selectedBank?.id.toString(),
                                currentState.narrationTFV.text.trim(),
                                currentState.accountNumberType,
                                ""
                            )
                        )
                    )
                }
            }
        }
    }
}