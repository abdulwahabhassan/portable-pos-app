package com.bankly.banklykozenpos.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.banklykozenpos.R
import com.bankly.banklykozenpos.model.QuickAction
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun HomeScreen() {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)

        ) {
            WalletCard(
                isWalletBalanceVisible = true,
                onToggleWalletBalanceVisibility = {},
                accountNumber = "02992002020",
                currentBalance = "2773892",
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.Start),
            text = stringResource(R.string.title_quick_action),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
            items(QuickAction.values()) { quickAction: QuickAction ->
                QuickActionCard(quickAction = quickAction, onClick = {})
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true
)
private fun HomeScreenPreview() {
    BanklyTheme {
        HomeScreen()
    }
}