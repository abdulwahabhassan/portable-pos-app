package com.bankly.feature.cardtransfer.ui.recipientdetails

import android.util.Log
import com.bankly.core.common.model.State
import com.bankly.core.common.util.DecimalFormatter
import com.bankly.core.common.util.Validator
import com.bankly.core.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterRecipientDetailsViewModel @Inject constructor(

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
                validateAccountNumber(
                    accountNumber = event.accountNumberTFV.text,
                    bankName = state.value.bankNameTFV.text
                )
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
                setUiState { copy(bankNameTFV = event.bankNameTFV) }
                validateAccountNumber(
                    accountNumber = state.value.accountNumberTFV.text,
                    bankName = event.bankNameTFV.text
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

    private fun validateAccountNumber(
        accountNumber: String,
        bankName: String
    ) {
        Log.d("debug validate account number", "$accountNumber, $bankName")
        if (accountNumber.isNotEmpty() &&
            Validator.isAccountNumberValid(accountNumber.trim()) &&
            bankName.isNotEmpty()
        )
            setUiState {
                copy(accountValidationState = State.Success("Hassan Abdulwahab"))
            }
    }
}