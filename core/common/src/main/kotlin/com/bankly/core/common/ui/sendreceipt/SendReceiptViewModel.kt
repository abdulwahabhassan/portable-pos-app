package com.bankly.core.common.ui.sendreceipt

import com.bankly.core.common.util.Validator.isPhoneNumberValid
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.feature.paywithtransfer.ui.sendreceipt.SendReceiptScreenEvent
import com.bankly.feature.paywithtransfer.ui.sendreceipt.SendReceiptScreenOneShotState
import com.bankly.feature.paywithtransfer.ui.sendreceipt.SendReceiptScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendReceiptViewModel @Inject constructor() :
    BaseViewModel<SendReceiptScreenEvent, SendReceiptScreenState, SendReceiptScreenOneShotState>(
        SendReceiptScreenState(),
    ) {

    override suspend fun handleUiEvents(event: SendReceiptScreenEvent) {
        when (event) {
            is SendReceiptScreenEvent.OnContinueClick -> {
                setOneShotState(SendReceiptScreenOneShotState.GoToSuccessfulScreen)
            }

            is SendReceiptScreenEvent.OnEnterPhoneNumber -> {
                setUiState {
                    copy(
                        phoneNumberTFV = event.phoneNumberTFV,
                        isPhoneNumberError = !isPhoneNumberValid(event.phoneNumberTFV.text),
                        phoneNumberFeedBack = if (isPhoneNumberValid(event.phoneNumberTFV.text)) {
                            ""
                        } else {
                            "Please enter a valid phone number"
                        },
                    )
                }
            }
        }
    }

    private suspend fun sendReceipt(phoneNumber: String) {
    }
}
