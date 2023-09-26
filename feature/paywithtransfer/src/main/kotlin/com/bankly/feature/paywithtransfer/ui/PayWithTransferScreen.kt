package com.bankly.feature.paywithtransfer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.feature.paywithtransfer.R
import com.bankly.feature.paywithtransfer.model.TransferAlert
import com.bankly.feature.paywithtransfer.ui.component.TransferAlertListItem

@Composable
fun PayWithTransferRoute(onBackPress: () -> Unit) {
    PayWithTransferScreen(
        onBackPress = onBackPress,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PayWithTransferScreen(onBackPress: () -> Unit) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.pay_with_transfer),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            stickyHeader {
                AccountDetailsView(onBackPress = onBackPress)
            }
            items(TransferAlert.mock()) { item: TransferAlert ->
                TransferAlertListItem(transferAlert = item)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PayWithTransferScreenPreview() {
    PayWithTransferScreen(onBackPress = { })
}
