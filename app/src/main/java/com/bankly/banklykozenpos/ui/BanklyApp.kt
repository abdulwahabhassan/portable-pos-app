package com.bankly.banklykozenpos.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.banklykozenpos.model.DashboardTab
import com.bankly.banklykozenpos.navigation.BanklyNavHost
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.icon.BanklyIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BanklyApp(
    networkMonitor: NetworkMonitor,
    appState: BanklyAppState = rememberBanklyAppState(networkMonitor = networkMonitor)
) {
    var selectedTab by remember { mutableStateOf(DashboardTab.POS) }
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (appState.shouldShowTopAppBar) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(50.dp))
                    Box(modifier = Modifier.fillMaxWidth(0.85f)) {
                        BanklyTabBar(
                            tabs = DashboardTab.values().toList(),
                            onTabClick = {
                                selectedTab = it
                            },
                            selectedTab = selectedTab
                        )
                    }
                    BanklyClickableIcon(
                        icon = BanklyIcons.Notification_Bell_01,
                        onClick = {}
                    )
                }
            }
        },
        bottomBar = {

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            BanklyNavHost(appState)
        }

    }
}