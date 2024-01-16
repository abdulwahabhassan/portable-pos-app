package com.bankly.feature.cardtransfer.ui.recipient

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.model.entity.Bank

internal sealed interface RecipientScreenEvent {
    class OnContinueClick(
        val accountNumber: String,
        val accountName: String,
        val amount: String,
        val bankName: String,
        val selectedBankId: Long?,
        val senderPhoneNumber: String,
    ) : RecipientScreenEvent

    class OnAccountNumber(val accountNumberTFV: TextFieldValue, val selectedBankId: Long?) :
        RecipientScreenEvent

    class OnAmount(val amountTFV: TextFieldValue) : RecipientScreenEvent
    class OnSelectBank(val bank: com.bankly.core.model.entity.Bank, val accountNumber: String) : RecipientScreenEvent
    class OnSenderPhoneNumber(val senderPhoneNumberTFV: TextFieldValue) :
        RecipientScreenEvent
    object OnDismissErrorDialog : RecipientScreenEvent
}
