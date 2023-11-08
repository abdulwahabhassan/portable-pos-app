package com.bankly.feature.networkchecker.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyExpandableList
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.BankNetwork

@Composable
internal fun BankNetworkSearchableListView(
    modifier: Modifier,
    isBankListLoading: Boolean,
    bankList: List<BankNetwork>,
) {
    var searchQuery by remember { mutableStateOf("") }
    val banks by remember(bankList, searchQuery) {
        mutableStateOf(bankList.filter { it.bankName.contains(searchQuery, true) })
    }
    val serviceDowntimeBanks by remember(banks) {
        mutableStateOf(banks.filter { it.networkPercentage <= 50 && it.totalCount > 0 })
    }
    val operationalBanks by remember(banks) {
        mutableStateOf(banks.filter { it.networkPercentage > 50 || it.totalCount == 0L })
    }
    if (isBankListLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(50.dp)
                    .size(50.dp),
                strokeWidth = 8.dp,
                trackColor = MaterialTheme.colorScheme.primaryContainer,
                strokeCap = StrokeCap.Round,
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            var isDowntimeBankListExpanded by rememberSaveable { mutableStateOf(true) }
            var isOperationalBankListExpanded by rememberSaveable { mutableStateOf(true) }

            Spacer(modifier = Modifier.height(8.dp))
            BanklySearchBar(
                modifier = Modifier,
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                    isDowntimeBankListExpanded = true
                    isOperationalBankListExpanded = true
                },
                searchPlaceholder = "Search bank",
            )
            if (serviceDowntimeBanks.isNotEmpty()) {
                BanklyExpandableList(
                    title = stringResource(com.bankly.feature.networkchecker.R.string.title_service_downtime_banks),
                    items = serviceDowntimeBanks,
                    isExpanded = isDowntimeBankListExpanded,
                    onClickExpand = {
                        isDowntimeBankListExpanded = !isDowntimeBankListExpanded
                    },
                    onItemSelected = {},
                    transformItemToString = { bank: BankNetwork -> bank.bankName },
                    drawItem = { item, _, _ ->
                        BankNetworkListItem(bankNetwork = item)
                    },
                    backgroundColor = MaterialTheme.colorScheme.background
                )
            }
            if (operationalBanks.isNotEmpty()) {
                BanklyExpandableList(
                    title = stringResource(com.bankly.feature.networkchecker.R.string.title_operational_banks),
                    items = operationalBanks,
                    isExpanded = isOperationalBankListExpanded,
                    onClickExpand = {
                        isOperationalBankListExpanded = !isOperationalBankListExpanded
                    },
                    onItemSelected = {},
                    transformItemToString = { bank: BankNetwork -> bank.bankName },
                    drawItem = { item, _, _ ->
                        BankNetworkListItem(bankNetwork = item)
                    },
                    backgroundColor = MaterialTheme.colorScheme.background
                )
            }

        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BankNetworkSearchableListPreview() {
    BanklyTheme {
        BankNetworkSearchableListView(
            modifier = Modifier,
            isBankListLoading = false,
            bankList = listOf(
                BankNetwork("GT Bank", "", 60.00, 100),
                BankNetwork("First Bank", "", 100.00, 59),
                BankNetwork("Access Bank", "", 50.00, 0)
            ),
        )
    }
}
