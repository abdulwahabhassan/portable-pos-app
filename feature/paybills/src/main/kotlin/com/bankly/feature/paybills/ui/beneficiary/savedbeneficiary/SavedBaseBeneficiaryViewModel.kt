package com.bankly.feature.paybills.ui.beneficiary.savedbeneficiary

import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBillPlansUseCase
import com.bankly.core.domain.usecase.GetBillProvidersUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.feature.paybills.ui.beneficiary.BaseBeneficiaryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SavedBaseBeneficiaryViewModel @Inject constructor(
    nameEnquiryUseCase: NameEnquiryUseCase,
    getBillProvidersUseCase: GetBillProvidersUseCase,
    getBillPlansUseCase: GetBillPlansUseCase,
    userPreferencesDataStore: UserPreferencesDataStore,
) : BaseBeneficiaryViewModel(
    nameEnquiryUseCase = nameEnquiryUseCase,
    getBillProvidersUseCase = getBillProvidersUseCase,
    getBillPlansUseCase = getBillPlansUseCase,
    userPreferencesDataStore = userPreferencesDataStore,
)
