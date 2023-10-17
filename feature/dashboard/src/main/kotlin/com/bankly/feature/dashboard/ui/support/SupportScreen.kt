package com.bankly.feature.dashboard.ui.support

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.dashboard.model.Feature
import com.bankly.feature.dashboard.model.SupportOption
import com.bankly.feature.dashboard.ui.component.FeatureCard
import com.bankly.feature.dashboard.ui.component.SupportListItem

@Composable
internal fun SupportRoute(
    onBackPress: () -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
) {
    SupportScreen(
        onBackPress = onBackPress,
        onSupportOptionClick = onSupportOptionClick
    )
}

@Composable
private fun SupportScreen(
    onBackPress: () -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
) {
    BackHandler {
        onBackPress()
    }
    LazyColumn() {
        items(SupportOption.values()) { supportOption: SupportOption ->
            SupportListItem(
                supportOption = supportOption, onClick = {
                    onSupportOptionClick(supportOption)
                }
            )
        }
    }
}

@Composable
private fun SupportScreenPreview() {
    BanklyTheme {
        SupportScreen(
            onBackPress = {},
            onSupportOptionClick = {}
        )
    }
}
