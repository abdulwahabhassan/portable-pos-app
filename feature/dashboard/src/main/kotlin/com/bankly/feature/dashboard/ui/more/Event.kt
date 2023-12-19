package com.bankly.feature.dashboard.ui.more

import com.bankly.core.entity.Feature

internal sealed interface MoreScreenEvent {
    object OnLogOutClick : MoreScreenEvent
    data class OnFeatureCardClick(val feature: Feature) : MoreScreenEvent
    object OnDismissLogOutWarningDialog : MoreScreenEvent
    object OnDismissFeatureAccessDeniedDialog : MoreScreenEvent
}
