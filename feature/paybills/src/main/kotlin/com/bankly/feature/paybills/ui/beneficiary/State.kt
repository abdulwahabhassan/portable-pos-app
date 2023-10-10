package com.bankly.feature.paybills.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.entity.BillPlan
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.entity.MeterNameEnquiry
import com.bankly.core.entity.BillProvider
import com.bankly.feature.paybills.model.BeneficiaryTab
import com.bankly.feature.paybills.model.BillType
import com.bankly.feature.paybills.model.SavedBeneficiary

internal data class BeneficiaryScreenState(
    val billType: BillType? = null,
    val isTypeError: Boolean = false,
    val typeFeedBack: String = "",
    val selectedBillProvider: BillProvider? = null,
    val isProviderError: Boolean = false,
    val providerFeedBack: String = "",
    val selectedBillPlan: BillPlan? = null,
    val isPlanError: Boolean = false,
    val planFeedBack: String = "",
    val phoneNumberTFV: TextFieldValue = TextFieldValue(text = ""),
    val isPhoneNumberError: Boolean = false,
    val phoneNumberFeedBack: String = "",
    val meterNumberTFV: TextFieldValue = TextFieldValue(text = ""),
    val isMeterNumberError: Boolean = false,
    val meterNumberFeedBack: String = "",
    val cableTvNumberTFV: TextFieldValue = TextFieldValue(text = ""),
    val isCableTvNumberError: Boolean = false,
    val cableTvNumberFeedBack: String = "",
    val amountTFV: TextFieldValue = TextFieldValue(text = ""),
    val isAmountError: Boolean = false,
    val amountFeedBack: String = "",
    val saveAsBeneficiary: Boolean = false,
    val meterNameEnquiry: MeterNameEnquiry? = null,
    val cableTvNameEnquiry: CableTvNameEnquiry? = null,
    val billProviderList: List<BillProvider> = emptyList(),
    val billPlanList: List<BillPlan> = emptyList(),
    val selectedTab: BeneficiaryTab = BeneficiaryTab.NEW_BENEFICIARY,
    val shouldShowSavedBeneficiaryList: Boolean = true,
    val savedBeneficiaries: List<SavedBeneficiary> = SavedBeneficiary.mockOtherBanks(),
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val isProviderListLoading: Boolean = false,
    val isPlanListLoading: Boolean = false,
    val validationStatusIcon: Int? = null,
    val isPaymentLoading: Boolean = false
) {
    private val isAnyLoading: Boolean
        get() = isProviderListLoading || isPlanListLoading || isPaymentLoading || isPaymentLoading

    val isContinueButtonEnabled: Boolean
        get() = when (billType) {
            BillType.AIRTIME -> {
                selectedBillProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isAnyLoading.not()
            }

            BillType.INTERNET_DATA -> {
                selectedBillProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        selectedBillPlan != null && planTFV.text.isNotEmpty() && isPlanError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isAnyLoading.not()
            }

            BillType.CABLE_TV -> {
                selectedBillProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        selectedBillPlan != null && planTFV.text.isNotEmpty() && isPlanError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isAnyLoading.not() &&
                        cableTvNumberTFV.text.isNotEmpty() && isCableTvNumberError.not() &&
                        cableTvNameEnquiry != null

            }

            BillType.ELECTRICITY -> {
                selectedBillProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        selectedBillPlan != null && planTFV.text.isNotEmpty() && isPlanError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isAnyLoading.not() &&
                        meterNumberTFV.text.isNotEmpty() && isMeterNumberError.not() &&
                        meterNameEnquiry != null
            }

            null -> false
        }

    val providerTFV: TextFieldValue
        get() = if (selectedBillProvider != null) {
            TextFieldValue(text = selectedBillProvider.name)
        } else {
            TextFieldValue(
                text = "",
            )
        }

    val planTFV: TextFieldValue
        get() = if (selectedBillPlan != null) {
            TextFieldValue(text = selectedBillPlan.description)
        } else {
            TextFieldValue(
                text = "",
            )
        }

    val isProviderFieldEnable: Boolean
        get() = isAnyLoading.not()

    val isPhoneNumberFieldEnabled: Boolean
        get() = isAnyLoading.not()

    val isCableTvNumberFieldEnabled: Boolean
        get() = isAnyLoading.not() && selectedBillPlan != null && validationStatusIcon != BanklyIcons.ValidationInProgress

    val isMeterNumberFieldEnabled: Boolean
        get() = isAnyLoading.not() && selectedBillPlan != null && validationStatusIcon != BanklyIcons.ValidationInProgress

    val isPlanFieldEnabled: Boolean
        get() = isAnyLoading.not() && providerTFV.text.isNotEmpty()

    val isSaveAsBeneficiarySwitchEnabled: Boolean
        get() = isAnyLoading.not()

    val isAmountFieldEnabled: Boolean
        get() = isAnyLoading.not() && when (billType) {
            BillType.AIRTIME, BillType.ELECTRICITY, null -> true
            BillType.INTERNET_DATA, BillType.CABLE_TV -> false
        }

    val showLoadingIndicator: Boolean
        get() = isAnyLoading
}

internal sealed interface BeneficiaryScreenOneShotState : OneShotState {
    data class GoToConfirmTransactionScreen(val transactionData: TransactionData) :
        BeneficiaryScreenOneShotState
}
