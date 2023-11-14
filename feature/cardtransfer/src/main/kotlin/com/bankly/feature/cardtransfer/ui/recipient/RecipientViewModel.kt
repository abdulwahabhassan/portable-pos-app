package com.bankly.feature.cardtransfer.ui.recipient

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.Validator
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.CardTransferAccountInquiryData
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.entity.Bank
import com.bankly.core.entity.AccountNameEnquiry
import com.bankly.core.entity.CardTransferAccountInquiry
import com.bankly.core.sealed.State
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
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
                performNameEnquiry(
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

            is RecipientScreenEvent.OnSelectBank -> {
                setUiState {
                    copy(
                        selectedBank = event.bank,
                    )
                }
                performNameEnquiry(
                    accountNumber = event.accountNumber,
                    bankId = event.bank.id,
                )
            }

            is RecipientScreenEvent.OnSenderPhoneNumber -> {
                setUiState {
                    val isEmpty = event.senderPhoneNumberTFV.text.trim().isEmpty()
                    val isValid =
                        if (isEmpty) true else Validator.isPhoneNumberValid(event.senderPhoneNumberTFV.text.trim())

                    copy(
                        senderPhoneNumberTFV = event.senderPhoneNumberTFV,
                        isSenderPhoneNumberError = isValid.not(),
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
                val amount = AmountFormatter().polish(event.amount).replace(",", "")
                    .toDouble()
                performAccountInquiry(
                    CardTransferAccountInquiryData(
                        event.selectedBankId ?: 0L,
                        event.accountNumber,
                        AmountFormatter().polish(event.amount).replace(",", "")
                            .toDouble(),
                        Tools.serialNumber,
                        Tools.terminalId,
                        "4",
                        Tools.deviceLocation ?: Tools.merchantLocation ?: "",
                        "POS"
                    ),
                    senderPhoneNumber = event.senderPhoneNumber,
                    amount
                )

            }

            RecipientScreenEvent.OnDismissErrorDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }
        }
    }

    private suspend fun performAccountInquiry(
        cardTransferAccountInquiryData: CardTransferAccountInquiryData,
        senderPhoneNumber: String,
        amount: Double
    ) {
        nameEnquiryUseCase.performCardTransferAccountInquiry(
            token = userPreferencesDataStore.data().token,
            body = cardTransferAccountInquiryData,
        ).onEach { resource ->
            resource.onLoading {
                setUiState {
                    copy(isAccountValidationLoading = true)
                }
            }
            resource.onReady { accountInquiry: CardTransferAccountInquiry ->
                Log.d("debug account inquiry", "account inquiry: $accountInquiry")
                if (accountInquiry.balance > cardTransferAccountInquiryData.amount) {
                    setUiState {
                        copy(isAccountValidationLoading = false)
                    }
                    setOneShotState(
                        RecipientScreenOneShotState.GoToSelectAccountTypeScreen(
                            transactionData = TransactionData.CardTransfer(
                                accountName = accountInquiry.accountName,
                                inquiryReference = accountInquiry.inquiryReference,
                                accountNumber = accountInquiry.accountNumber,
                                amount = amount,
                                sendersPhoneNumber = senderPhoneNumber,
                            ),
                        ),
                    )

                } else {
                    setUiState {
                        copy(
                            isAccountValidationLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = "You do not have sufficient balance in your wallet to perform this transaction"
                        )
                    }
                }
            }
            resource.onFailure { message ->
                Log.d("debug account inquiry", "account inquiry error message: $message")
                setUiState {
                    copy(
                        showErrorDialog = true,
                        errorDialogMessage = message
                    )
                }
            }
        }.catch {
            it.printStackTrace()
            setUiState {
                copy(
                    showErrorDialog = true,
                    errorDialogMessage = it.message ?: "An unexpected error occurred"
                )
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getBanks() {
        getBanksUseCase.invoke(userPreferencesDataStore.data().token)
            .onEach { resource ->
                resource.onLoading {
                    setUiState {
                        copy(isBankListLoading = true)
                    }
                }
                resource.onReady { banks: List<Bank> ->
                    setUiState {
                        copy(isBankListLoading = false, banks = banks)
                    }
                }
                resource.onFailure { message ->
                    setUiState {
                        copy(
                            isBankListLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = message
                        )
                    }
                }
            }
            .catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isBankListLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: ""
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun performNameEnquiry(
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
                        copy(
                            isNameInquiryLoading = true,
                            validationIcon = BanklyIcons.ValidationInProgress
                        )
                    }
                }
                resource.onReady { accountNameEnquiry: AccountNameEnquiry ->
                    setUiState {
                        copy(
                            isNameInquiryLoading = false,
                            accountNumberFeedBack = accountNameEnquiry.accountName,
                            isAccountNumberError = false,
                            validationIcon = BanklyIcons.ValidationPassed
                        )
                    }
                }
                resource.onFailure { message ->
                    setUiState {
                        copy(
                            isNameInquiryLoading = false,
                            validationIcon = BanklyIcons.ValidationFailed,
                            accountNumberFeedBack = message,
                            isAccountNumberError = true,
                        )
                    }
                }
            }.catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isNameInquiryLoading = false,
                        validationIcon = BanklyIcons.ValidationFailed,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "An unexpected error occurred",
                        isAccountNumberError = true,
                    )
                }

            }.launchIn(viewModelScope)
        }
    }
}
