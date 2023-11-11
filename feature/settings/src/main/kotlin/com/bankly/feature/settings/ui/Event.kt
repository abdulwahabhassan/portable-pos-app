package com.bankly.feature.settings.ui

import com.bankly.core.entity.Feature

internal sealed interface SettingsScreenEvent {
    data class OnFeatureToggle(val toggledFeature: Feature, val features: List<Feature>) : SettingsScreenEvent
    object LoadUiData : SettingsScreenEvent
}
