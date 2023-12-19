package com.bankly.feature.settings.ui

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferences
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.entity.Feature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
) :
    BaseViewModel<SettingsScreenEvent, SettingsScreenState, SettingsScreenOneShotState>(
        SettingsScreenState(),
    ) {

    override suspend fun handleUiEvents(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.OnFeatureToggle -> {
                userPreferencesDataStore.update {
                    copy(
                        featureToggleList = event.features.map { feature ->
                            if (event.toggledFeature.title == feature.title) {
                                val inverse = event.toggledFeature.isEnabled.not()
                                when (feature) {
                                    is Feature.CardTransfer -> feature.copy(enabled = inverse)
                                    is Feature.CheckBalance -> feature.copy(enabled = inverse)
                                    is Feature.EndOfDay -> feature.copy(enabled = inverse)
                                    is Feature.Float -> feature.copy(enabled = inverse)
                                    is Feature.NetworkChecker -> feature.copy(enabled = inverse)
                                    is Feature.PayBills -> feature.copy(enabled = inverse)
                                    is Feature.PayWithCard -> feature.copy(enabled = inverse)
                                    is Feature.PayWithTransfer -> feature.copy(enabled = inverse)
                                    is Feature.PayWithUssd -> feature.copy(enabled = inverse)
                                    is Feature.SendMoney -> feature.copy(enabled = inverse)
                                    is Feature.Settings -> feature.copy(enabled = inverse)
                                }
                            } else {
                                feature
                            }
                        },
                    )
                }
            }

            SettingsScreenEvent.LoadUiData -> {
                userPreferencesDataStore.flow().onEach { userPreference: UserPreferences ->
                    setUiState { copy(features = userPreference.featureToggleList) }
                }.launchIn(viewModelScope)
            }
        }
    }
}
