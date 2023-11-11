package com.bankly.feature.settings.ui

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.Feature
import com.bankly.core.entity.FeatureToggle

internal data class SettingsScreenState(
    val features:  List<Feature> = emptyList(),
)

internal sealed interface SettingsScreenOneShotState : OneShotState