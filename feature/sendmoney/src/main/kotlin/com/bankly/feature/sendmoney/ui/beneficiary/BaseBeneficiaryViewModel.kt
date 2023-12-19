package com.bankly.feature.sendmoney.ui.beneficiary

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.Validator
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.entity.Bank
import com.bankly.core.sealed.State
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import com.bankly.core.sealed.onSessionExpired
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal abstract class BaseBeneficiaryViewModel constructor(
    private val nameEnquiryUseCase: NameEnquiryUseCase,
    private val getBanksUseCase: GetBanksUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<BeneficiaryScreenEvent, BeneficiaryScreenState, BeneficiaryScreenOneShotState>(
    BeneficiaryScreenState(),
) {
    init {
        viewModelScope.launch { getBanks() }
    }

    override suspend fun handleUiEvents(event: BeneficiaryScreenEvent) {
        when (event) {
            is BeneficiaryScreenEvent.OnInputAccountOrPhoneNumber -> {
                val isEmpty = event.accountOrPhoneNumberTFV.text.trim().isEmpty()
                val isValid =
                    if (event.accountNumberType == AccountNumberType.PHONE_NUMBER) {
                        Validator.isPhoneNumberValid(
                            event.accountOrPhoneNumberTFV.text.trim(),
                        )
                    } else {
                        Validator.isAccountNumberValid(event.accountOrPhoneNumberTFV.text.trim())
                    }

                setUiState {
                    copy(
                        accountOrPhoneTFV = event.accountOrPhoneNumberTFV,
                        isAccountOrPhoneError = isEmpty || isValid.not(),
                        accountOrPhoneFeedBack = if (isEmpty) {
                            "Please enter ${if (event.accountNumberType == AccountNumberType.PHONE_NUMBER) "phone" else "account"} number"
                        } else if (isValid.not()) {
                            "Please enter a valid ${if (event.accountNumberType == AccountNumberType.PHONE_NUMBER) "phone" else "account"} number"
                        } else {
                            ""
                        },
                    )
                }

                if (isValid && isEmpty.not()) {
                    doNameEnquiry(
                        number = event.accountOrPhoneNumberTFV.text,
                        bankId = event.selectedBankId,
                        channel = event.sendMoneyChannel,
                        accountNumberType = event.accountNumberType,
                    )
                }
            }

            is BeneficiaryScreenEvent.OnInputAmount -> {
                val polishedAmount = AmountFormatter().polish(event.amountTFV.text)
                val isEmpty = polishedAmount.isEmpty()
                val isValid = if (isEmpty) {
                    false
                } else {
                    Validator.isAmountValid(
                        polishedAmount.replace(",", "").toDouble(),
                    )
                }

                setUiState {
                    copy(
                        amountTFV = event.amountTFV.copy(polishedAmount),
                        isAmountError = isEmpty || isValid.not(),
                        amountFeedBack = if (isEmpty) {
                            "Please enter amount"
                        } else if (isValid.not()) {
                            "Please enter a valid amount"
                        } else {
                            ""
                        },
                    )
                }
            }

            is BeneficiaryScreenEvent.OnSelectBank -> {
                setUiState {
                    copy(
                        selectedBank = event.bank,
                    )
                }

                validateAccountNumber(
                    accountNumber = event.accountOrPhoneNumber,
                    bankId = event.bank.id,
                )
            }

            is BeneficiaryScreenEvent.OnTypeSelected -> {
                setUiState { copy(accountNumberType = event.accountNumberType) }
                doNameEnquiry(
                    number = event.accountOrPhoneNumber,
                    bankId = event.bankId,
                    channel = SendMoneyChannel.BANKLY_TO_BANKLY,
                    accountNumberType = event.accountNumberType,
                )
            }

            is BeneficiaryScreenEvent.OnInputNarration -> {
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
                        shouldShowSavedBeneficiaryList = false,
                        selectedBank = Bank(
                            event.savedBeneficiary.bankName,
                            event.savedBeneficiary.bankId,
                        ),
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
                setOneShotState(
                    BeneficiaryScreenOneShotState.GoToConfirmTransactionScreen(
                        transactionData = TransactionData.BankTransfer(
                            accountName = event.accountName,
                            amount = AmountFormatter().polish(event.amount).replace(",", "")
                                .toDouble(),
                            bankName = event.bankName,
                            bankId = event.selectedBankId.toString(),
                            narration = event.narration.trim(),
                            accountNumberType = event.accountNumberType,
                            transactionPin = "",
                            accountNumber = event.accountOrPhoneNumber,
                        ),
                    ),
                )
            }
        }
    }

    private suspend fun getBanks() {
        getBanksUseCase.invoke(userPreferencesDataStore.data().token)
            .onEach { resource ->
                resource.onLoading {
                    setUiState {
                        copy(bankListState = State.Loading)
                    }
                }
                resource.onReady { banks: List<Bank> ->
                    setUiState {
                        copy(bankListState = State.Success(banks))
                    }
                }
                resource.onFailure { message ->
                    setUiState {
                        copy(bankListState = State.Error(message))
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(BeneficiaryScreenOneShotState.OnSessionExpired)
                }
            }
            .catch {
                it.printStackTrace()
                setUiState {
                    copy(bankListState = State.Error(it.message ?: "An unexpected error occurred"))
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun doNameEnquiry(
        number: String,
        bankId: Long?,
        channel: SendMoneyChannel,
        accountNumberType: AccountNumberType,
    ) {
        if (channel == SendMoneyChannel.BANKLY_TO_OTHER || accountNumberType == AccountNumberType.ACCOUNT_NUMBER) {
            validateAccountNumber(
                accountNumber = number,
                bankId = when (channel) {
                    SendMoneyChannel.BANKLY_TO_OTHER -> bankId
                    SendMoneyChannel.BANKLY_TO_BANKLY -> BANKLY_BANK_ID
                },
            )
        } else {
            validatePhoneNumber(phoneNumber = number)
        }
    }

    private suspend fun validateAccountNumber(
        accountNumber: String,
        bankId: Long?,
    ) {
        val isEmpty = accountNumber.trim().isEmpty()
        val isValid = Validator.isAccountNumberValid(accountNumber.trim())

        if (bankId != null && isEmpty.not() && isValid) {
            nameEnquiryUseCase.performBankAccountNameEnquiry(
                userPreferencesDataStore.data().token,
                accountNumber,
                bankId.toString(),
            ).onEach { resource ->
                resource.onLoading {
                    setUiState {
                        copy(accountOrPhoneValidationState = State.Loading)
                    }
                }
                resource.onReady { accountNameEnquiry: AccountNameEnquiry ->
                    setUiState {
                        copy(
                            accountOrPhoneValidationState = State.Success(accountNameEnquiry),
                            accountOrPhoneFeedBack = accountNameEnquiry.accountName,
                            isAccountOrPhoneError = false,
                        )
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(
                            accountOrPhoneValidationState = State.Error(message),
                            accountOrPhoneFeedBack = message,
                            isAccountOrPhoneError = true,
                        )
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(BeneficiaryScreenOneShotState.OnSessionExpired)
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        accountOrPhoneValidationState = State.Error(
                            it.message ?: "An unexpected error occurred",
                        ),
                    )
                }
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun validatePhoneNumber(phoneNumber: String) {
        if (phoneNumber.isNotEmpty()) {
            nameEnquiryUseCase.performBankAccountNameEnquiry(
                userPreferencesDataStore.data().token,
                phoneNumber,
            ).onEach { resource ->
                resource.onLoading {
                    setUiState {
                        copy(accountOrPhoneValidationState = State.Loading)
                    }
                }
                resource.onReady { accountNameEnquiry: AccountNameEnquiry ->
                    setUiState {
                        copy(
                            accountOrPhoneValidationState = State.Success(accountNameEnquiry),
                            accountOrPhoneFeedBack = accountNameEnquiry.accountName,
                            isAccountOrPhoneError = false,
                        )
                    }
                }
                resource.onFailure { message ->
                    setUiState {
                        copy(
                            accountOrPhoneValidationState = State.Error(message),
                            accountOrPhoneFeedBack = message,
                            isAccountOrPhoneError = true,
                        )
                    }
                }
                resource.onSessionExpired {
                    setOneShotState(BeneficiaryScreenOneShotState.OnSessionExpired)
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        accountOrPhoneValidationState = State.Error(
                            it.message ?: "An unexpected error occurred",
                        ),
                    )
                }
            }.launchIn(viewModelScope)
        }
    }
}
