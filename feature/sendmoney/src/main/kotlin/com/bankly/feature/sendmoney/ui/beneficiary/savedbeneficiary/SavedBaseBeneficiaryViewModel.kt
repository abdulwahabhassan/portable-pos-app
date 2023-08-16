package com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary

import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetBanksUseCase
import com.bankly.core.domain.usecase.NameEnquiryUseCase
import com.bankly.feature.sendmoney.ui.beneficiary.BaseBeneficiaryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedBaseBeneficiaryViewModel @Inject constructor(
    nameEnquiryUseCase: NameEnquiryUseCase,
    getBanksUseCase: GetBanksUseCase,
    userPreferencesDataStore: UserPreferencesDataStore
) : BaseBeneficiaryViewModel(
    nameEnquiryUseCase, getBanksUseCase, userPreferencesDataStore
)