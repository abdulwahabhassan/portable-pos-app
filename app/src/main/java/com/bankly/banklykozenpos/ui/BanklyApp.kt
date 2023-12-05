package com.bankly.banklykozenpos.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.bankly.banklykozenpos.navigation.AppNavHost

@Composable
fun BanklyApp(
    appState: BanklyAppState = rememberBanklyAppState(),
    onCloseApp: () -> Unit,
    activity: Activity
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
        }
    )
}
