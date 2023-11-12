package com.bankly.feature.eod.ui.dashboard

import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class EodDashboardViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<EodDashboardScreenEvent, EodDashboardScreenState, EodDashboardScreenOneShotState>(
    EodDashboardScreenState()
) {
    override suspend fun handleUiEvents(event: EodDashboardScreenEvent) {
        when (event) {
            EodDashboardScreenEvent.LoadUiData -> {

            }
        }
    }
}

