package com.bankly.feature.dashboard.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.dashboard.model.DashboardTab

@Composable
fun DashBoardAppBar(
    selectedTab: DashboardTab,
    onTabChange: (selectedTab: DashboardTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.size(24.dp))
        Box(modifier = Modifier.weight(1f)) {
            BanklyTabBar(
                tabs = DashboardTab.values().toList(),
                selectedTab = selectedTab,
                onTabClick = { tab: DashboardTab ->
                    onTabChange(tab)
                },
            )
        }
        BanklyClickableIcon(
            modifier = Modifier,
            icon = BanklyIcons.Notification_Bell_01,
            onClick = {},
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun DashboardAppBarPreview() {
    BanklyTheme {
        DashBoardAppBar(selectedTab = DashboardTab.POS, onTabChange = {})
    }
}