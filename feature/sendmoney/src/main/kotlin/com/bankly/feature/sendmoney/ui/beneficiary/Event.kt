package com.bankly.feature.sendmoney.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.model.Bank
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.feature.sendmoney.model.SavedBeneficiary
import com.bankly.feature.sendmoney.model.SendMoneyChannel
import com.bankly.feature.sendmoney.model.Type

sealed interface BeneficiaryDetailsScreenEvent {
    class OnContinueClick(
        val type: Type,
        val accountOrPhoneNumber: String,
        val amount: String,
        val narration: String,
        val saveAsBeneficiary: Boolean
    ) : BeneficiaryDetailsScreenEvent

    class OnEnterAccountOrPhoneNumber(
        val accountOrPhoneNumberTFV: TextFieldValue,
        val sendMoneyChannel: SendMoneyChannel,
    ) :
        BeneficiaryDetailsScreenEvent

    class OnSelectBank(
        val bank: Bank
    ) : BeneficiaryDetailsScreenEvent

    class OnEnterAmount(val amountTFV: TextFieldValue) : BeneficiaryDetailsScreenEvent
    class OnTypeSelected(val typeTFV: TextFieldValue) : BeneficiaryDetailsScreenEvent
    class OnToggleSaveAsBeneficiary(val toggleState: Boolean) : BeneficiaryDetailsScreenEvent
    class OnEnterNarration(val narrationTFV: TextFieldValue) :
        BeneficiaryDetailsScreenEvent

    class OnTabSelected(val tab: BeneficiaryTab) : BeneficiaryDetailsScreenEvent
    object OnExit : BeneficiaryDetailsScreenEvent
    class OnBeneficiarySelected(val savedBeneficiary: SavedBeneficiary) :
        BeneficiaryDetailsScreenEvent
    object OnChangeSelectedSavedBeneficiary: BeneficiaryDetailsScreenEvent
}