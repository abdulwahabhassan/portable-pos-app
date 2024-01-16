package com.bankly.feature.dashboard.ui.more

import com.bankly.core.model.entity.Feature

internal sealed interface MoreScreenEvent {
    object OnLogOutClick : MoreScreenEvent
    data class OnFeatureCardClick(val feature: com.bankly.core.model.entity.Feature) : MoreScreenEvent
    object OnDismissLogOutWarningDialog : MoreScreenEvent
    object OnDismissFeatureAccessDeniedDialog : MoreScreenEvent
}
