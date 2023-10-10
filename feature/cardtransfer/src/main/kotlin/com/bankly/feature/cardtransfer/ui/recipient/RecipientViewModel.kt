package com.bankly.feature.cardtransfer.ui.recipient

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.TransactionType
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.Validator
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.entity.Bank
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.sealed.State
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RecipientViewModel @Inject constructor(
    private val nameEnquiryUseCase: NameEnquiryUseCase,
    private val getBanksUseCase: GetBanksUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<RecipientScreenEvent, RecipientScreenState, RecipientScreenOneShotState>(
    RecipientScreenState(),
) {

    init {
        viewModelScope.launch { getBanks() }
    }

    override suspend fun handleUiEvents(event: RecipientScreenEvent) {
        when (event) {
            is RecipientScreenEvent.OnAccountNumber -> {
                val isEmpty = event.accountNumberTFV.text.trim().isEmpty()
                val isValid = Validator.isAccountNumberValid(event.accountNumberTFV.text.trim())
                setUiState {
                    copy(
                        accountNumberTFV = event.accountNumberTFV,
                        isAccountNumberError = isEmpty || isValid.not(),
                        accountNumberFeedBack = if (isEmpty) {
                            "Please enter account number"
                        } else if (isValid.not()) {
                            "Please enter a valid account number"
                        } else {
                            ""
                        },
                    )
                }
                validateAccountNumber(
                    accountNumber = event.accountNumberTFV.text,
                    bankId = event.selectedBankId,
                )
            }

            is RecipientScreenEvent.OnAmount -> {
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

            RecipientScreenEvent.OnExit -> {
                setUiState { copy(accountValidationState = State.Initial) }
            }

            is RecipientScreenEvent.OnSelectBank -> {
                setUiState {
                    copy(
                        selectedBank = event.bank,
                    )
                }
                validateAccountNumber(
                    accountNumber = event.accountNumber,
                    bankId = event.bank.id,
                )
            }

            is RecipientScreenEvent.OnSenderPhoneNumber -> {
                setUiState {
                    val isEmpty = event.senderPhoneNumberTFV.text.trim().isEmpty()
                    val isValid =
                        Validator.isPhoneNumberValid(event.senderPhoneNumberTFV.text.trim())
                    copy(
                        senderPhoneNumberTFV = event.senderPhoneNumberTFV,
                        isSenderPhoneNumberError = isEmpty || isValid.not(),
                        senderPhoneNumberFeedBack = if (isEmpty) {
                            "Please enter phone number"
                        } else if (isValid.not()) {
                            "Please enter a valid phone number"
                        } else {
                            ""
                        },
                    )
                }
            }

            is RecipientScreenEvent.OnContinueClick -> {
                setOneShotState(
                    RecipientScreenOneShotState.GoToSelectAccountTypeScreen(
                        TransactionData.mockCardTransferTransactionData(),
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
            }
            .catch {
                it.printStackTrace()
                setUiState {
                    copy(bankListState = State.Error(it.message ?: "An unexpected error occurred"))
                }
            }
            .launchIn(viewModelScope)
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
                        copy(accountValidationState = State.Loading)
                    }
                }
                resource.onReady { accountNameEnquiry: AccountNameEnquiry ->
                    setUiState {
                        copy(
                            accountValidationState = State.Success(accountNameEnquiry),
                            accountNumberFeedBack = accountNameEnquiry.accountName,
                            isAccountNumberError = false,
                        )
                    }
                }
                resource.onFailure { message ->
                    Log.d("debug getBanks", "(onFailure) message: $message")
                    setUiState {
                        copy(
                            accountValidationState = State.Error(message),
                            accountNumberFeedBack = message,
                            isAccountNumberError = true,
                        )
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        accountValidationState = State.Error(
                            it.message ?: "An unexpected error occurred",
                        ),
                    )
                }
            }.launchIn(viewModelScope)
        }
    }
}
