package com.bankly.feature.paybills.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.entity.Plan
import com.bankly.core.entity.Provider
import com.bankly.feature.paybills.model.BeneficiaryTab
import com.bankly.feature.paybills.model.BillType
import com.bankly.feature.paybills.model.SavedBeneficiary

internal sealed interface BeneficiaryScreenEvent {
    data class FetchProviders(val billType: BillType) : BeneficiaryScreenEvent
    data class OnSelectProvider(val provider: Provider, val billType: BillType) : BeneficiaryScreenEvent
    data class OnInputAmount(val amountTFV: TextFieldValue) : BeneficiaryScreenEvent
    class OnInputMeterNumber(val meterNumberTFV: TextFieldValue, val providerId: Long, val planId: Long) : BeneficiaryScreenEvent
    class OnInputCableTvNumber(val cableTvNumber: TextFieldValue, val providerId: Long) : BeneficiaryScreenEvent
    data class OnToggleSaveAsBeneficiary(val toggleState: Boolean) : BeneficiaryScreenEvent
    data class OnTabSelected(val tab: BeneficiaryTab) : BeneficiaryScreenEvent
    data class OnBeneficiarySelected(val savedBeneficiary: SavedBeneficiary) : BeneficiaryScreenEvent
    object OnChangeSelectedSavedBeneficiary : BeneficiaryScreenEvent
    object OnDismissDialog : BeneficiaryScreenEvent

    data class OnContinueClick(
        val billType: BillType,
        val phoneNumber: String,
        val amount: String,
    ) : BeneficiaryScreenEvent

    data class OnInputPhoneNumber(
        val phoneNumberTFV: TextFieldValue,
        val billType: BillType,
    ) : BeneficiaryScreenEvent

    class OnSelectPlan(val billType: BillType, val plan: Plan) :
        BeneficiaryScreenEvent

    class UpdateBillType(val billType: BillType) :
        BeneficiaryScreenEvent


}
