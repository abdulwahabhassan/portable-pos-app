package com.bankly.feature.paybills.ui.beneficiary

import androidx.compose.ui.text.input.TextFieldValue
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.entity.Plan
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.entity.MeterNameEnquiry
import com.bankly.core.entity.Provider
import com.bankly.feature.paybills.model.BeneficiaryTab
import com.bankly.feature.paybills.model.BillType
import com.bankly.feature.paybills.model.SavedBeneficiary

internal data class BeneficiaryScreenState(
    val billType: BillType? = null,
    val isTypeError: Boolean = false,
    val typeFeedBack: String = "",
    val selectedProvider: Provider? = null,
    val isProviderError: Boolean = false,
    val providerFeedBack: String = "",
    val selectedPlan: Plan? = null,
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
    val providerList: List<Provider> = emptyList(),
    val planList: List<Plan> = emptyList(),
    val selectedTab: BeneficiaryTab = BeneficiaryTab.NEW_BENEFICIARY,
    val shouldShowSavedBeneficiaryList: Boolean = true,
    val savedBeneficiaries: List<SavedBeneficiary> = SavedBeneficiary.mockOtherBanks(),
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val isProviderListLoading: Boolean = false,
    val isPlanListLoading: Boolean = false,
    val isEnquiryLoading: Boolean = false,
    val validationStatusIcon: Int? = null,
) {
    private val isLoading: Boolean
        get() = isProviderListLoading || isPlanListLoading || isEnquiryLoading

    val isContinueButtonEnabled: Boolean
        get() = when (billType) {
            BillType.AIRTIME -> {
                selectedProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isLoading.not()
            }

            BillType.INTERNET_DATA -> {
                selectedProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        selectedPlan != null && planTFV.text.isNotEmpty() && isPlanError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isLoading.not()
            }

            BillType.CABLE_TV -> {
                selectedProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        selectedPlan != null && planTFV.text.isNotEmpty() && isPlanError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isLoading.not() &&
                        cableTvNumberTFV.text.isNotEmpty() && isCableTvNumberError.not() &&
                        cableTvNameEnquiry != null

            }

            BillType.ELECTRICITY -> {
                selectedProvider != null && providerTFV.text.isNotEmpty() && isProviderError.not() &&
                        selectedPlan != null && planTFV.text.isNotEmpty() && isPlanError.not() &&
                        phoneNumberTFV.text.isNotEmpty() && isPhoneNumberError.not() &&
                        amountTFV.text.isNotEmpty() && isAmountError.not() && isLoading.not() &&
                        meterNumberTFV.text.isNotEmpty() && isMeterNumberError.not() &&
                        meterNameEnquiry != null
            }

            null -> false
        }

    val providerTFV: TextFieldValue
        get() = if (selectedProvider != null) {
            TextFieldValue(text = selectedProvider.name)
        } else {
            TextFieldValue(
                text = "",
            )
        }

    val planTFV: TextFieldValue
        get() = if (selectedPlan != null) {
            TextFieldValue(text = selectedPlan.description)
        } else {
            TextFieldValue(
                text = "",
            )
        }

    val isProviderFieldEnable: Boolean
        get() = isLoading.not()

    val isPhoneNumberFieldEnabled: Boolean
        get() = isLoading.not()

    val isCableTvNumberFieldEnabled: Boolean
        get() = isLoading.not() && selectedPlan != null && validationStatusIcon != BanklyIcons.ValidationInProgress

    val isMeterNumberFieldEnabled: Boolean
        get() = isLoading.not() && selectedPlan != null && validationStatusIcon != BanklyIcons.ValidationInProgress

    val isPlanFieldEnabled: Boolean
        get() = isLoading.not() && providerTFV.text.isNotEmpty()

    val isSaveAsBeneficiarySwitchEnabled: Boolean
        get() = isLoading.not()

    val isAmountFieldEnabled: Boolean
        get() = isLoading.not() && when (billType) {
            BillType.AIRTIME, BillType.ELECTRICITY, null -> true
            BillType.INTERNET_DATA, BillType.CABLE_TV -> false
        }

    val showLoadingIndicator: Boolean
        get() = isLoading
}

internal sealed interface BeneficiaryScreenOneShotState : OneShotState {
    data class GoToConfirmTransactionScreen(val transactionData: TransactionData) :
        BeneficiaryScreenOneShotState
}
