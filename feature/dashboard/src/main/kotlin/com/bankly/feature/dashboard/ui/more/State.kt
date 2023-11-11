package com.bankly.feature.dashboard.ui.more

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.entity.Feature

internal data class MoreScreenState(
    val showLogoutWarningDialog: Boolean = false,
    val showFeatureAccessDeniedDialog: Boolean = false,
)

sealed interface MoreScreenOneShotState : OneShotState {
    data class GoToFeature(val feature: Feature) : MoreScreenOneShotState
}