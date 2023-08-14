package com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.State
import com.bankly.core.common.model.onFailure
import com.bankly.core.common.model.onLoading
import com.bankly.core.common.model.onReady
import com.bankly.core.common.util.DecimalFormatter
import com.bankly.core.common.util.Validator
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.model.Bank
import com.bankly.core.model.NameEnquiry
import com.bankly.feature.sendmoney.model.SendMoneyChannel
import com.bankly.feature.sendmoney.model.Type
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryDetailsScreenEvent
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class SavedBeneficiaryDetailsViewModel @Inject constructor(
    private val nameEnquiryUseCase: NameEnquiryUseCase,
    private val getBanksUseCase: GetBanksUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) :
    BaseViewModel<BeneficiaryDetailsScreenEvent, BeneficiaryDetailsScreenState>(
        BeneficiaryDetailsScreenState()
    ) {

    init {
        viewModelScope.launch { getBanks() }
    }

    override suspend fun handleUiEvents(event: BeneficiaryDetailsScreenEvent) {
        when (event) {
            is BeneficiaryDetailsScreenEvent.OnContinueClick -> {

            }

            is BeneficiaryDetailsScreenEvent.OnEnterAccountOrPhoneNumber -> {

                val isEmpty = event.accountOrPhoneNumberTFV.text.trim().isEmpty()
                val isValid = if (uiState.value.type == Type.PHONE_NUMBER) Validator.isPhoneNumberValid(event.accountOrPhoneNumberTFV.text.trim())
                else Validator.isAccountNumberValid(event.accountOrPhoneNumberTFV.text.trim())

                setUiState {
                    copy(
                        accountOrPhoneTFV = event.accountOrPhoneNumberTFV,
                        isAccountOrPhoneError = isEmpty || isValid.not(),
                        accountOrPhoneFeedBack = if (isEmpty) "Please enter ${if (uiState.value.type == Type.PHONE_NUMBER) "phone" else "account"} number"
                        else if (isValid.not()) "Please enter a valid ${if (uiState.value.type == Type.PHONE_NUMBER) "phone" else "account"} number"
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

            is BeneficiaryDetailsScreenEvent.OnEnterAmount -> {

                val cleanedUpAmount = DecimalFormatter().cleanup(event.amountTFV.text)
                val isEmpty = cleanedUpAmount.isEmpty()
                val isValid = if (isEmpty) false else Validator.isAmountValid(cleanedUpAmount.replace(",", "").toDouble())

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

            is BeneficiaryDetailsScreenEvent.OnSelectBank -> {
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

            BeneficiaryDetailsScreenEvent.OnExit -> {
                setUiState { copy(accountOrPhoneValidationState = State.Initial) }
            }

            is BeneficiaryDetailsScreenEvent.OnTypeSelected -> {
                setUiState { copy(typeTFV = event.typeTFV) }
            }

            is BeneficiaryDetailsScreenEvent.OnEnterNarration -> {
                setUiState { copy(narrationTFV = event.narrationTFV) }
            }

            is BeneficiaryDetailsScreenEvent.OnToggleSaveAsBeneficiary -> {
                setUiState { copy(saveAsBeneficiary = event.toggleState) }
            }

            is BeneficiaryDetailsScreenEvent.OnTabSelected -> {
                setUiState {
                    copy(selectedTab = event.tab)
                }
            }

            is BeneficiaryDetailsScreenEvent.OnBeneficiarySelected -> {
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

            is BeneficiaryDetailsScreenEvent.OnChangeSelectedSavedBeneficiary -> {
                setUiState { copy(shouldShowSavedBeneficiaryList = true) }
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

    private suspend fun doNameEnquiry(number: String, bankId: Long?, channel: SendMoneyChannel) {
        Log.d("debug doNameEnquiry", "doNameEnquiry called")
        when (uiState.value.type) {
            Type.ACCOUNT_NUMBER -> {
                when (channel) {
                    SendMoneyChannel.BANKLY_TO_OTHER -> {
                        validateAccountNumber(
                            accountNumber = number,
                            bankId = bankId
                        )
                    }
                    SendMoneyChannel.BANKLY_TO_BANKLY -> {
                        validatePhoneNumber(phoneNumber = number,)
                    }
                }
            }
            Type.PHONE_NUMBER -> { validatePhoneNumber(phoneNumber = number,) }
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
                        copy(accountOrPhoneValidationState = State.Success(nameEnquiry.accountName))
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(accountOrPhoneValidationState = State.Error(message))
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
                    copy(accountOrPhoneValidationState = State.Success(nameEnquiry.accountName))
                }
            }
            resource.onFailure { message ->
                Log.d("debug getBanks", "(onFailure) message: $message")
                setUiState {
                    copy(accountOrPhoneValidationState = State.Error(message))
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