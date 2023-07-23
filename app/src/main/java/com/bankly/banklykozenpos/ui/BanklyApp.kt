package com.bankly.banklykozenpos.ui

import androidx.compose.runtime.Composable
import com.bankly.banklykozenpos.navigation.AppNavHost
import com.bankly.core.data.util.NetworkMonitor


@Composable
fun BanklyApp(
    networkMonitor: NetworkMonitor,
    appState: BanklyAppState = rememberBanklyAppState(
        networkMonitor = networkMonitor
    ),
    onCloseApp: () -> Unit
) {
    AppNavHost(
        appState,
        onBackPress = onCloseApp
    )
}