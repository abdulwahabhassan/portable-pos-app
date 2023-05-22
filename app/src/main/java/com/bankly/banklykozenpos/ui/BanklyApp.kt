package com.bankly.banklykozenpos.ui

import android.content.Context
import androidx.compose.runtime.Composable
import com.bankly.banklykozenpos.navigation.TopLevelNavHost
import com.bankly.core.data.util.NetworkMonitor


@Composable
fun BanklyApp(
    context: Context,
    networkMonitor: NetworkMonitor,
    appState: BanklyAppState = rememberBanklyAppState(
        context = context,
        networkMonitor = networkMonitor
    ),
    onFinishActivity: () -> Unit
) {
    TopLevelNavHost(
        appState,
        onPopAuthenticationNavGraph = onFinishActivity,
        onPopDashBoardNavGraph = onFinishActivity
    )
}