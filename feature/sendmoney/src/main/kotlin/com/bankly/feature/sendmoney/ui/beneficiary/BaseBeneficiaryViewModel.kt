package com.bankly.feature.sendmoney.ui.beneficiary

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.sealed.State
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.entity.Bank
import com.bankly.core.entity.NameEnquiry
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.TransactionType
import com.bankly.core.common.util.DecimalFormatter
import com.bankly.core.common.util.Validator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseBeneficiaryViewModel constructor(
    private val nameEnquiryUseCase: NameEnquiryUseCase,
    private val getBanksUseCase: GetBanksUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : BaseViewModel<BeneficiaryScreenEvent, BeneficiaryScreenState, BeneficiaryScreenOneShotState>(
    BeneficiaryScreenState()
) {
    init {
        viewModelScope.launch { getBanks() }
    }

    override suspend fun handleUiEvents(event: BeneficiaryScreenEvent) {
        when (event) {
            is BeneficiaryScreenEvent.OnInputAccountOrPhoneNumber -> {

                val isEmpty = event.accountOrPhoneNumberTFV.text.trim().isEmpty()
                val isValid =
                    if (event.accountNumberType == AccountNumberType.PHONE_NUMBER) Validator.isPhoneNumberValid(
                        event.accountOrPhoneNumberTFV.text.trim()
                    ) else Validator.isAccountNumberValid(event.accountOrPhoneNumberTFV.text.trim())

                setUiState {
                    copy(
                        accountOrPhoneTFV = event.accountOrPhoneNumberTFV,
                        isAccountOrPhoneError = isEmpty || isValid.not(),
                        accountOrPhoneFeedBack = if (isEmpty) "Please enter ${if (event.accountNumberType == AccountNumberType.PHONE_NUMBER) "phone" else "account"} number"
                        else if (isValid.not()) "Please enter a valid ${if (event.accountNumberType == AccountNumberType.PHONE_NUMBER) "phone" else "account"} number"
                        else ""
                    )
                }

                if (isValid && isEmpty.not()) {
                    doNameEnquiry(
                        number = event.accountOrPhoneNumberTFV.text,
                        bankId = event.selectedBankId,
                        channel = event.sendMoneyChannel,
                        accountNumberType = event.accountNumberType
                    )
                }
            }

            is BeneficiaryScreenEvent.OnInputAmount -> {

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
                        selectedBank = event.bank
                    )
                }
                validateAccountNumber(
                    accountNumber = event.accountOrPhoneNumber,
                    bankId = event.bank.id
                )
            }

            is BeneficiaryScreenEvent.OnTypeSelected -> {
                setUiState { copy(accountNumberType = event.accountNumberType) }
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
                        selectedBank = Bank(event.savedBeneficiary.bankName, event.savedBeneficiary.bankId)
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
                        transactionData = TransactionData(
                            transactionType = when (event.sendMoneyChannel) {
                                SendMoneyChannel.BANKLY_TO_BANKLY -> TransactionType.BANK_TRANSFER_INTERNAL
                                SendMoneyChannel.BANKLY_TO_OTHER -> TransactionType.BANK_TRANSFER_EXTERNAL
                            },
                            phoneOrAccountNumber = event.accountOrPhoneNumber,
                            accountName = event.accountName,
                            amount = DecimalFormatter().cleanup(event.amount).replace(",", "")
                                .toDouble(),
                            vat = 0.00,
                            fee = 0.00,
                            bankName = event.bankName,
                            bankId = event.selectedBankId.toString(),
                            narration = event.narration.trim(),
                            accountNumberType = event.accountNumberType,
                            pin = ""
                        )
                    )
                )

            }
        }
    }

    private suspend fun getBanks() {
        getBanksUseCase.invoke(userPreferencesDataStore.data().token)
            .onEach { resource ->
                resource.onLoading {
                    Log.d("debug getBanks", "(onLoading) ...")
                    setUiState {
                        copy(bankListState = State.Loading)
                    }
                }
                resource.onReady { banks: List<Bank> ->
                    Log.d("debug getBanks", "(onReady) banks: $banks")
                    setUiState {
                        copy(bankListState = State.Success(banks))
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(bankListState = State.Error(message))
                    }
                }
            }
            .catch {
                Log.d("debug getBanks", "(error caught) message: ${it.message}")
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
        accountNumberType: AccountNumberType
    ) {
        Log.d("debug doNameEnquiry", "doNameEnquiry called")
        if (channel == SendMoneyChannel.BANKLY_TO_OTHER || accountNumberType == AccountNumberType.ACCOUNT_NUMBER) {
            validateAccountNumber(
                accountNumber = number,
                bankId = when (channel) {
                    SendMoneyChannel.BANKLY_TO_OTHER -> bankId
                    SendMoneyChannel.BANKLY_TO_BANKLY -> BANKLY_BANK_ID
                }
            )
        } else {
            validatePhoneNumber(phoneNumber = number)
        }
    }


    private suspend fun validateAccountNumber(
        accountNumber: String,
        bankId: Long?
    ) {
        Log.d("debug validateAccountNumber", "account number: $accountNumber, bank id: $bankId")
        if (bankId != null && accountNumber.isNotEmpty()) {
            nameEnquiryUseCase.performNameEnquiry(
                userPreferencesDataStore.data().token,
                accountNumber,
                bankId.toString()
            ).onEach { resource ->
                resource.onLoading {
                    Log.d("debug getBanks", "(onLoading) ...")
                    setUiState {
                        copy(accountOrPhoneValidationState = State.Loading)
                    }
                }
                resource.onReady { nameEnquiry: NameEnquiry ->
                    Log.d("debug getBanks", "(onReady) banks: $nameEnquiry")
                    setUiState {
                        copy(
                            accountOrPhoneValidationState = State.Success(nameEnquiry),
                            accountOrPhoneFeedBack = nameEnquiry.accountName,
                            isAccountOrPhoneError = false
                        )
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(
                            accountOrPhoneValidationState = State.Error(message),
                            accountOrPhoneFeedBack = message,
                            isAccountOrPhoneError = true
                        )
                    }
                }
            }.catch {
                Log.d("debug getBanks", "(error caught) message: ${it.message}")
                it.printStackTrace()
                setUiState {
                    copy(
                        accountOrPhoneValidationState = State.Error(
                            it.message ?: "An unexpected error occurred"
                        )
                    )
                }
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun validatePhoneNumber(phoneNumber: String) {
        Log.d("debug validatePhoneNumber", "phoneNumber: $phoneNumber")
        nameEnquiryUseCase.performNameEnquiry(
            userPreferencesDataStore.data().token,
            phoneNumber
        ).onEach { resource ->
            resource.onLoading {
                Log.d("debug getBanks", "(onLoading) ...")
                setUiState {
                    copy(accountOrPhoneValidationState = State.Loading)
                }
            }
            resource.onReady { nameEnquiry: NameEnquiry ->
                Log.d("debug getBanks", "(onReady) banks: $nameEnquiry")
                setUiState {
                    copy(
                        accountOrPhoneValidationState = State.Success(nameEnquiry),
                        accountOrPhoneFeedBack = nameEnquiry.accountName,
                        isAccountOrPhoneError = false
                    )
                }
            }
            resource.onFailure { message ->
                Log.d("debug getBanks", "(onFailure) message: $message")
                setUiState {
                    copy(
                        accountOrPhoneValidationState = State.Error(message),
                        accountOrPhoneFeedBack = message,
                        isAccountOrPhoneError = true
                    )
                }
            }
        }.catch {
            Log.d("debug getBanks", "(error caught) message: ${it.message}")
            it.printStackTrace()
            setUiState {
                copy(
                    accountOrPhoneValidationState = State.Error(
                        it.message ?: "An unexpected error occurred"
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        const val BANKLY_BANK_ID: Long = 96
    }

}