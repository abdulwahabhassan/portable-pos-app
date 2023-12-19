package com.bankly.feature.eod.ui.synceod

internal sealed interface SyncEodScreenEvent {
    object LoadUiData : SyncEodScreenEvent
    object OnSyncTapEod : SyncEodScreenEvent
    object OnDismissErrorDialog : SyncEodScreenEvent
    object OnDismissSuccessfulEodSyncDialog : SyncEodScreenEvent
}
