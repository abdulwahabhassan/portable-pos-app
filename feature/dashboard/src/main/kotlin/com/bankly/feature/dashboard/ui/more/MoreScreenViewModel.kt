package com.bankly.feature.dashboard.ui.more

import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MoreScreenViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<MoreScreenEvent, MoreScreenState, MoreScreenOneShotState>(MoreScreenState()) {

    override suspend fun handleUiEvents(event: MoreScreenEvent) {
        when (event) {
            is MoreScreenEvent.OnFeatureCardClick -> {
                val feature = userPreferencesDataStore.data().featureToggleList.find {
                    it.title == event.feature.title
                }
                if (feature?.isEnabled == true) {
                    setOneShotState(MoreScreenOneShotState.GoToFeature(event.feature))
                } else {
                    setUiState { copy(showFeatureAccessDeniedDialog = true) }
                }
            }

            is MoreScreenEvent.OnLogOutClick -> {
                setUiState { copy(showLogoutWarningDialog = true) }
            }

            MoreScreenEvent.OnDismissLogOutWarningDialog -> {
                setUiState { copy(showLogoutWarningDialog = false) }
            }

            MoreScreenEvent.OnDismissFeatureAccessDeniedDialog -> {
                setUiState { copy(showFeatureAccessDeniedDialog = false) }
            }
        }
    }

}