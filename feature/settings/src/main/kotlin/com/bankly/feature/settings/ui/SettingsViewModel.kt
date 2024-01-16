package com.bankly.feature.settings.ui

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferences
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.model.entity.Feature
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
                val updatedToggleFeatureList = event.features.map { feature ->
                    if (event.toggledFeature.title == feature.title) {
                        val inverse = event.toggledFeature.isEnabled.not()
                        when (feature) {
                            is com.bankly.core.model.entity.Feature.CardTransfer -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.CheckBalance -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.EndOfDay -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.Float -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.NetworkChecker -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.PayBills -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.PayWithCard -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.PayWithTransfer -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.PayWithUssd -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.SendMoney -> feature.copy(enabled = inverse)
                            is com.bankly.core.model.entity.Feature.Settings -> feature
                        }
                    } else {
                        feature
                    }
                }
                userPreferencesDataStore.update {
                    copy(
                        featureToggleList = updatedToggleFeatureList,
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
