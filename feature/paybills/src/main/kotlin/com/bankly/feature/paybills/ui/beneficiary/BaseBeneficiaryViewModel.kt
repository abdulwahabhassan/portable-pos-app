package com.bankly.feature.paybills.ui.beneficiary

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.model.TransactionType
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.Validator
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.ValidateCableTvNumberData
import com.bankly.core.data.ValidateElectricityMeterNumberData
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.domain.usecase.GetBillPlansUseCase
import com.bankly.core.domain.usecase.GetBillProvidersUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.core.entity.BillPlan
import com.bankly.core.entity.BillProvider
import com.bankly.core.entity.CableTvNameEnquiry
import com.bankly.core.enums.BillsPlanType
import com.bankly.core.enums.BillsProviderType
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onLoading
import com.bankly.core.sealed.onReady
import com.bankly.feature.paybills.model.BillType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal abstract class BaseBeneficiaryViewModel constructor(
    private val nameEnquiryUseCase: NameEnquiryUseCase,
    private val getBillProvidersUseCase: GetBillProvidersUseCase,
    private val getBillPlansUseCase: GetBillPlansUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<BeneficiaryScreenEvent, BeneficiaryScreenState, BeneficiaryScreenOneShotState>(
    BeneficiaryScreenState(),
) {
    private val cableTvNameEnquiryIdentifierFlow: MutableStateFlow<ValidateCableTvNumberData?> =
        MutableStateFlow(null)
    private val meterNameEnquiryIdentifierFlow: MutableStateFlow<ValidateElectricityMeterNumberData?> =
        MutableStateFlow(null)

    init {
        cableTvNameEnquiryIdentifierFlow
            .debounce(5000)
            .filterNotNull()
            .filterNot { validateCableTvNumberData ->
                validateCableTvNumberData.cardNumber.isEmpty()
            }
            .onEach { validateCableTvNumberData ->
                validateCableTvNumber(validateCableTvNumberData)

            }.launchIn(viewModelScope)

        meterNameEnquiryIdentifierFlow
            .debounce(5000)
            .filterNotNull()
            .filterNot { validateElectricityMeterNumberData ->
                validateElectricityMeterNumberData.meterNumber.isEmpty()
            }
            .onEach { validateElectricityMeterNumberData ->
                validateMeterNumber(validateElectricityMeterNumberData)
            }.launchIn(viewModelScope)
    }

    override suspend fun handleUiEvents(event: BeneficiaryScreenEvent) {
        when (event) {
            is BeneficiaryScreenEvent.OnInputPhoneNumber -> {
                val isEmpty = event.phoneNumberTFV.text.trim().isEmpty()
                val isValid = Validator.isPhoneNumberValid(event.phoneNumberTFV.text.trim())

                setUiState {
                    copy(
                        phoneNumberTFV = event.phoneNumberTFV,
                        isPhoneNumberError = isEmpty || isValid.not(),
                        phoneNumberFeedBack = if (isEmpty) {
                            "Please enter phone number"
                        } else if (isValid.not()) {
                            "Please enter a valid phone number"
                        } else {
                            ""
                        },
                    )
                }
            }

            is BeneficiaryScreenEvent.OnInputAmount -> {
                val polishedAmount = AmountFormatter().polish(event.amountTFV.text)
                val isEmpty = polishedAmount.isEmpty()
                val isValid = if (isEmpty) {
                    false
                } else {
                    Validator.isAmountValid(
                        minimumAmount = event.minimumAmount ?: 0.00,
                        amount = polishedAmount.replace(",", "").toDouble(),
                    )
                }

                setUiState {
                    copy(
                        amountTFV = event.amountTFV.copy(polishedAmount),
                        isAmountError = isEmpty || isValid.not(),
                        amountFeedBack = if (isEmpty) {
                            "Please enter amount"
                        } else if (isValid.not()) {
                            "Please enter a valid amount"
                        } else {
                            ""
                        },
                    )
                }
            }

            is BeneficiaryScreenEvent.OnSelectProvider -> {
                setUiState {
                    copy(
                        selectedBillProvider = event.billProvider,
                        selectedBillPlan = null,
                        billPlanList = emptyList(),
                    )
                }
                getPlans(event.billType, event.billProvider.id)
            }

            is BeneficiaryScreenEvent.OnToggleSaveAsBeneficiary -> {
                setUiState { copy(saveAsBeneficiary = !event.toggleState) }
            }

            is BeneficiaryScreenEvent.OnTabSelected -> {
                setUiState {
                    copy(selectedTab = event.tab)
                }
            }

            is BeneficiaryScreenEvent.OnBeneficiarySelected -> {
                setUiState {
                    copy(
                        phoneNumberTFV = phoneNumberTFV.copy(text = event.savedBeneficiary.uniqueId),
                        shouldShowSavedBeneficiaryList = false,
                        selectedBillProvider = null,
                    )
                }
            }

            is BeneficiaryScreenEvent.OnChangeSelectedSavedBeneficiary -> {
                setUiState { copy(shouldShowSavedBeneficiaryList = true) }
            }

            is BeneficiaryScreenEvent.OnContinueClick -> {
                setOneShotState(
                    BeneficiaryScreenOneShotState.GoToConfirmTransactionScreen(
                        transactionData = TransactionData.BillPayment(
                            billsProviderType =  when (event.billType) {
                                BillType.AIRTIME -> BillsProviderType.AIRTIME
                                BillType.INTERNET_DATA -> BillsProviderType.INTERNET_DATA
                                BillType.CABLE_TV -> BillsProviderType.CABLE_TV
                                BillType.ELECTRICITY -> BillsProviderType.ELECTRICITY
                            },
                            phoneNumber = event.phoneNumber,
                            amount = AmountFormatter().polish(event.amount).replace(",", "")
                                .toDouble(),
                            transactionPin = "",
                            billId = event.billProvider?.id?.toString() ?: "",
                            billItemId = event.billPlan?.id?.toString() ?: "",
                            billProvider = event.billProvider?.name ?: "",
                            billPlan = event.billPlan?.name ?: "",
                            cableTvNumberOrMeterNumber = event.cableTvNumberOrMeterNumber ?: "",
                            cableTvOwnerNameOrMeterOwnerName = event.cableTvOwnerNameOrMeterOwnerName
                        ),
                    ),
                )
            }

            is BeneficiaryScreenEvent.OnInputCableTvNumber -> {
                setUiState {
                    copy(
                        cableTvNumberTFV = event.cableTvNumber,
                        cableTvNumberFeedBack = "",
                        isCableTvNumberError = false,
                        cableTvNameEnquiry = null,
                        validationStatusIcon = null,
                    )
                }
                cableTvNameEnquiryIdentifierFlow.value =
                    ValidateCableTvNumberData(
                        cardNumber = event.cableTvNumber.text,
                        billId = event.providerId
                    )
            }

            is BeneficiaryScreenEvent.OnInputMeterNumber -> {
                setUiState {
                    copy(
                        meterNumberTFV = event.meterNumberTFV,
                        meterNumberFeedBack = "",
                        isMeterNumberError = false,
                        meterNameEnquiry = null,
                        validationStatusIcon = null
                    )
                }
                meterNameEnquiryIdentifierFlow.value = ValidateElectricityMeterNumberData(
                    meterNumber = event.meterNumberTFV.text,
                    billId = event.providerId,
                    billItemId = event.planId
                )
            }

            is BeneficiaryScreenEvent.OnSelectPlan -> {
                val polishedAmount = AmountFormatter().polish(event.billPlan.amount.toString())
                setUiState {
                    copy(
                        selectedBillPlan = event.billPlan,
                        amountTFV = TextFieldValue(polishedAmount)
                    )
                }
            }

            is BeneficiaryScreenEvent.FetchProviders -> {
                Log.d("debug fetch providers", "FetchProviders called")
                getProviders(billType = event.billType)
            }

            is BeneficiaryScreenEvent.UpdateBillType -> {
                setUiState { copy(billType = event.billType) }
            }

            BeneficiaryScreenEvent.OnDismissDialog -> {
                setUiState { copy(showErrorDialog = false, errorDialogMessage = "") }
            }
        }
    }

    private suspend fun getProviders(billType: BillType) {
        val billsProviderType = when (billType) {
            BillType.AIRTIME -> BillsProviderType.AIRTIME
            BillType.INTERNET_DATA -> BillsProviderType.INTERNET_DATA
            BillType.CABLE_TV -> BillsProviderType.CABLE_TV
            BillType.ELECTRICITY -> BillsProviderType.ELECTRICITY
        }
        getBillProvidersUseCase.invoke(userPreferencesDataStore.data().token, billsProviderType)
            .onEach { resource ->
                resource.onLoading {
                    setUiState {
                        copy(isProviderListLoading = true)
                    }
                }
                resource.onReady { billProviders: List<BillProvider> ->
                    setUiState {
                        copy(billProviderList = billProviders, isProviderListLoading = false)
                    }
                }
                resource.onFailure { message ->
                    setUiState {
                        copy(
                            isProviderListLoading = false,
                            showErrorDialog = true,
                            errorDialogMessage = message
                        )
                    }
                }
            }
            .catch {
                it.printStackTrace()
                setUiState {
                    copy(
                        isProviderListLoading = false,
                        showErrorDialog = true,
                        errorDialogMessage = it.message ?: "Request could not be completed"
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun getPlans(billType: BillType, billId: Long) {
        val billPlanType = when (billType) {
            BillType.INTERNET_DATA -> BillsPlanType.INTERNET_DATA
            BillType.CABLE_TV -> BillsPlanType.CABLE_TV
            BillType.ELECTRICITY -> BillsPlanType.ELECTRICITY
            BillType.AIRTIME -> null
        }
        if (billPlanType != null) {
            getBillPlansUseCase.invoke(userPreferencesDataStore.data().token, billPlanType, billId)
                .onEach { resource ->
                    resource.onLoading {
                        setUiState {
                            copy(isPlanListLoading = true)
                        }
                    }
                    resource.onReady { billPlans: List<BillPlan> ->
                        setUiState {
                            copy(billPlanList = billPlans, isPlanListLoading = false)
                        }
                    }
                    resource.onFailure { message ->
                        setUiState {
                            copy(
                                isPlanListLoading = false,
                                showErrorDialog = true,
                                errorDialogMessage = message
                            )
                        }
                    }
                }
                .catch {
                    it.printStackTrace()
                    setUiState {
                        copy(
                            isPlanListLoading = false,
                            showErrorDialog = false,
                            errorDialogMessage = it.message ?: "Request could not be completed"
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }


    private suspend fun validateMeterNumber(
        data: ValidateElectricityMeterNumberData
    ) {
        nameEnquiryUseCase.performElectricMeterNameEnquiry(
            token = userPreferencesDataStore.data().token,
            body = data,
        ).onEach { resource ->
            resource.onLoading {
                setUiState {
                    copy(
                        validationStatusIcon = BanklyIcons.ValidationInProgress,
                    )
                }
            }
            resource.onReady {meterNameEnquiry ->
                setUiState {
                    copy(
                        validationStatusIcon = BanklyIcons.ValidationPassed,
                        meterNameEnquiry = meterNameEnquiry,
                        meterNumberFeedBack = "${meterNameEnquiry.customerName}, ${meterNameEnquiry.address}"
                    )
                }
            }
            resource.onFailure { message ->
                setUiState {
                    copy(
                        validationStatusIcon = BanklyIcons.ValidationFailed,
                        showErrorDialog = true,
                        errorDialogMessage = message,
                        isMeterNumberError = true,
                    )
                }
            }
        }.catch {
            it.printStackTrace()
            setUiState {
                copy(
                    validationStatusIcon = BanklyIcons.ValidationFailed,
                    showErrorDialog = true,
                    errorDialogMessage = it.message ?: "Request could not be processed",
                    isMeterNumberError = true,
                )
            }

        }.launchIn(viewModelScope)
    }

    private suspend fun validateCableTvNumber(data: ValidateCableTvNumberData) {
        nameEnquiryUseCase.performCableTvNameEnquiry(
            token = userPreferencesDataStore.data().token,
            body = data,
        ).onEach { resource ->
            resource.onLoading {
                setUiState {
                    copy(
                        validationStatusIcon = BanklyIcons.ValidationInProgress,
                    )
                }
            }
            resource.onReady { cableTvNameEnquiry: CableTvNameEnquiry ->
                setUiState {
                    copy(
                        validationStatusIcon = BanklyIcons.ValidationPassed,
                        cableTvNameEnquiry = cableTvNameEnquiry,
                        cableTvNumberFeedBack = cableTvNameEnquiry.customerName
                    )
                }
            }
            resource.onFailure { message ->
                setUiState {
                    copy(
                        validationStatusIcon = BanklyIcons.ValidationFailed,
                        showErrorDialog = true,
                        errorDialogMessage = message,
                        isCableTvNumberError = true,
                    )
                }
            }
        }.catch {
            it.printStackTrace()
            setUiState {
                copy(
                    validationStatusIcon = BanklyIcons.ValidationFailed,
                    showErrorDialog = true,
                    errorDialogMessage = it.message ?: "Request could not be processed",
                    isCableTvNumberError = true,
                )
            }

        }.launchIn(viewModelScope)
    }
}
