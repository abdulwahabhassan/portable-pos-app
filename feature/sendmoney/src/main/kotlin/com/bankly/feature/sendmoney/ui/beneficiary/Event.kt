package com.bankly.feature.sendmoney.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.model.Bank
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.feature.sendmoney.model.SavedBeneficiary
import com.bankly.feature.sendmoney.model.SendMoneyChannel

sealed interface BeneficiaryScreenEvent {
    class OnEnterAccountOrPhoneNumber(
        val accountOrPhoneNumberTFV: TextFieldValue,
        val sendMoneyChannel: SendMoneyChannel,
    ) :
        BeneficiaryScreenEvent

    class OnSelectBank(
        val bank: Bank
    ) : BeneficiaryScreenEvent

    class OnEnterAmount(val amountTFV: TextFieldValue) : BeneficiaryScreenEvent
    class OnTypeSelected(val typeTFV: TextFieldValue) : BeneficiaryScreenEvent
    class OnToggleSaveAsBeneficiary(val toggleState: Boolean) : BeneficiaryScreenEvent
    class OnEnterNarration(val narrationTFV: TextFieldValue) :
        BeneficiaryScreenEvent

    class OnTabSelected(val tab: BeneficiaryTab) : BeneficiaryScreenEvent
    class OnBeneficiarySelected(val savedBeneficiary: SavedBeneficiary) :
        BeneficiaryScreenEvent
    object OnChangeSelectedSavedBeneficiary: BeneficiaryScreenEvent
    class OnContinueClick(val sendMoneyChannel: SendMoneyChannel) : BeneficiaryScreenEvent
}