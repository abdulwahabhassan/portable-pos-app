package com.bankly.feature.settings.ui

import com.bankly.core.model.entity.Feature

internal sealed interface SettingsScreenEvent {
    data class OnFeatureToggle(val toggledFeature: com.bankly.core.model.entity.Feature, val features: List<com.bankly.core.model.entity.Feature>) : SettingsScreenEvent
    object LoadUiData : SettingsScreenEvent
}
