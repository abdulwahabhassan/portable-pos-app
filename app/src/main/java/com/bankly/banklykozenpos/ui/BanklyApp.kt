package com.bankly.banklykozenpos.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.bankly.banklykozenpos.navigation.AppNavHost
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.feature.notification.model.TransactionPayload

@Composable
internal fun BanklyApp(
    appState: BanklyAppState,
    onCloseApp: () -> Unit,
    activity: Activity,
    onClosNotificationMessageDialog: (NotificationMessage) -> Unit,
    onCloseTransactionAlertDialog: (TransactionPayload) -> Unit,
) {
    var isSessionExpired: Boolean by rememberSaveable { mutableStateOf(false) }

    AppNavHost(
        appState,
        onExitApp = onCloseApp,
        activity = activity,
        isSessionExpired = isSessionExpired,
        onSessionExpired = {
            isSessionExpired = true
        },
        onSessionRenewed = {
            isSessionExpired = false
        },
        onClosNotificationMessageDialog = onClosNotificationMessageDialog,
        onCloseTransactionAlertDialog = onCloseTransactionAlertDialog,
    )


}
