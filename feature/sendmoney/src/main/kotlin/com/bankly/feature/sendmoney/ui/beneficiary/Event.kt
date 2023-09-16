package com.bankly.feature.sendmoney.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.entity.Bank
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.feature.sendmoney.model.SavedBeneficiary

internal sealed interface BeneficiaryScreenEvent {
    data class OnSelectBank(val bank: Bank, val accountOrPhoneNumber: String) : BeneficiaryScreenEvent
    data class OnInputAmount(val amountTFV: TextFieldValue) : BeneficiaryScreenEvent
    data class OnTypeSelected(val accountOrPhoneNumber: String, val bankId: Long?, val accountNumberType: AccountNumberType) : BeneficiaryScreenEvent
    data class OnToggleSaveAsBeneficiary(val toggleState: Boolean) : BeneficiaryScreenEvent
    data class OnInputNarration(val narrationTFV: TextFieldValue) : BeneficiaryScreenEvent
    data class OnTabSelected(val tab: BeneficiaryTab) : BeneficiaryScreenEvent
    data class OnBeneficiarySelected(val savedBeneficiary: SavedBeneficiary) : BeneficiaryScreenEvent
    object OnChangeSelectedSavedBeneficiary : BeneficiaryScreenEvent
    data class OnContinueClick(
        val sendMoneyChannel: SendMoneyChannel,
        val accountOrPhoneNumber: String,
        val accountName: String,
        val amount: String,
        val bankName: String,
        val selectedBankId: Long?,
        val narration: String,
        val accountNumberType: AccountNumberType,
    ) : BeneficiaryScreenEvent

    data class OnInputAccountOrPhoneNumber(
        val accountOrPhoneNumberTFV: TextFieldValue,
        val sendMoneyChannel: SendMoneyChannel,
        val selectedBankId: Long?,
        val accountNumberType: AccountNumberType,
    ) : BeneficiaryScreenEvent
}
