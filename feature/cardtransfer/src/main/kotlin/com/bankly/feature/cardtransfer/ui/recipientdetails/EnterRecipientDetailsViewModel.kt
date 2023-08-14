package com.bankly.feature.cardtransfer.ui.recipientdetails

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
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.model.NameEnquiry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class EnterRecipientDetailsViewModel @Inject constructor(
    private val nameEnquiryUseCase: NameEnquiryUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : BaseViewModel<EnterRecipientDetailsScreenEvent, EnterRecipientDetailsScreenState>(
    EnterRecipientDetailsScreenState()
) {

    override suspend fun handleUiEvents(event: EnterRecipientDetailsScreenEvent) {
        when (event) {
            is EnterRecipientDetailsScreenEvent.OnContinueClick -> {

            }

            is EnterRecipientDetailsScreenEvent.OnEnterAccountNumber -> {
                val isEmpty = event.accountNumberTFV.text.trim().isEmpty()
                val isValid = Validator.isAccountNumberValid(event.accountNumberTFV.text.trim())
                setUiState {
                    copy(
                        accountNumberTFV = event.accountNumberTFV,
                        isAccountNumberError = isEmpty || isValid.not(),
                        accountNumberFeedBack = if (isEmpty) "Please enter account number"
                        else if (isValid.not()) "Please enter a valid account number"
                        else ""
                    )
                }
                if (isValid && isEmpty.not()) {
                    validateAccountNumber(
                        accountNumber = event.accountNumberTFV.text,
                        bankId = uiState.value.selectedBank?.id
                    )
                }

            }

            is EnterRecipientDetailsScreenEvent.OnEnterAmount -> {
                val cleanedUpAmount = DecimalFormatter().cleanup(event.amountTFV.text)
                val isEmpty = cleanedUpAmount.isEmpty()
                val isValid = if (isEmpty) false
                else Validator.isAmountValid(
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

            EnterRecipientDetailsScreenEvent.OnExit -> {
                setUiState { copy(accountValidationState = State.Initial) }
            }

            is EnterRecipientDetailsScreenEvent.OnSelectBank -> {
                setUiState { copy(
                    bankNameTFV = bankNameTFV.copy(text = event.bank.name),
                    selectedBank = event.bank
                ) }
                validateAccountNumber(
                    accountNumber = uiState.value.accountNumberTFV.text,
                    bankId = event.bank.id
                )
            }

            is EnterRecipientDetailsScreenEvent.OnEnterSenderPhoneNumber -> {
                setUiState {
                    val isEmpty = event.senderPhoneNumberTFV.text.trim().isEmpty()
                    val isValid =
                        Validator.isPhoneNumberValid(event.senderPhoneNumberTFV.text.trim())
                    copy(
                        senderPhoneNumberTFV = event.senderPhoneNumberTFV,
                        isSenderPhoneNumberError = isEmpty || isValid.not(),
                        senderPhoneNumberFeedBack = if (isEmpty) "Please enter phone number"
                        else if (isValid.not()) "Please enter a valid phone number"
                        else ""
                    )
                }
            }
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
                        copy(accountValidationState = State.Loading)
                    }
                }
                resource.onReady { nameEnquiry: NameEnquiry ->
                    Log.d("debug getBanks", "(onReady) banks: $nameEnquiry")
                    setUiState {
                        copy(accountValidationState = State.Success(nameEnquiry.accountName))
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(accountValidationState = State.Error(message))
                    }
                }
            }.catch {
                Log.d("debug getBanks", "(error caught) message: ${it.message}")
                it.printStackTrace()
                setUiState {
                    copy(
                        accountValidationState = State.Error(
                            it.message ?: "An unexpected error occurred"
                        )
                    )
                }
            }.launchIn(viewModelScope)
        }
    }
}