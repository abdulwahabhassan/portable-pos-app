package com.bankly.feature.cardtransfer.ui.recipientdetails

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.entity.Bank

sealed interface EnterRecipientDetailsScreenEvent {
    class OnContinueClick(
        val bankName: String,
        val accountNumber: String,
        val amount: String,
        val senderPhoneNumber: String
    ) : EnterRecipientDetailsScreenEvent

    class OnEnterAccountNumber(val accountNumberTFV: TextFieldValue) :
        EnterRecipientDetailsScreenEvent

    class OnEnterAmount(val amountTFV: TextFieldValue) : EnterRecipientDetailsScreenEvent
    class OnSelectBank(val bank: Bank) : EnterRecipientDetailsScreenEvent
    class OnEnterSenderPhoneNumber(val senderPhoneNumberTFV: TextFieldValue) :
        EnterRecipientDetailsScreenEvent

    object OnExit : EnterRecipientDetailsScreenEvent


}