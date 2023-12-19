package com.bankly.feature.networkchecker.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.ui.view.EmptyStateView
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.BankNetwork
import com.bankly.feature.networkchecker.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BankNetworkSearchableListView(
    modifier: Modifier,
    isBankListLoading: Boolean,
    bankList: List<BankNetwork>,
) {
    val searchQuery by remember { mutableStateOf("") }
    val banks by remember(bankList, searchQuery) {
        mutableStateOf(bankList.filter { it.bankName.contains(searchQuery, true) })
    }
    val serviceDowntimeBanks by remember(banks) {
        mutableStateOf(banks.filter { it.networkPercentage <= 50 && it.totalCount > 0 })
    }
    val operationalBanks by remember(banks) {
        mutableStateOf(banks.filter { it.networkPercentage > 50 || it.totalCount == 0L })
    }
    var isDowntimeBankListExpanded by rememberSaveable { mutableStateOf(true) }
    var isOperationalBankListExpanded by rememberSaveable { mutableStateOf(true) }

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
    } else if (banks.isEmpty()) {
        EmptyStateView()
    } else {
        LazyColumn(modifier = modifier.fillMaxSize(), content = {
            if (serviceDowntimeBanks.isNotEmpty()) {
                stickyHeader {
                    ListHeader(
                        isListExpanded = isDowntimeBankListExpanded,
                        title = stringResource(id = R.string.title_service_downtime_banks),
                        onClickVisibilityIcon = {
                            isDowntimeBankListExpanded =
                                !isDowntimeBankListExpanded
                        },
                    )
                }
                if (isDowntimeBankListExpanded) {
                    items(serviceDowntimeBanks) {
                        BankNetworkListItem(bankNetwork = it)
                    }
                }
            }

            if (operationalBanks.isNotEmpty()) {
                stickyHeader {
                    ListHeader(
                        isListExpanded = isOperationalBankListExpanded,
                        title = stringResource(id = R.string.title_operational_banks),
                        onClickVisibilityIcon = {
                            isOperationalBankListExpanded =
                                !isOperationalBankListExpanded
                        },
                    )
                }
                if (isOperationalBankListExpanded) {
                    items(operationalBanks) {
                        BankNetworkListItem(bankNetwork = it)
                    }
                }
            }
        })
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
                BankNetwork("Access Bank", "", 50.00, 0),
            ),
        )
    }
}

@Composable
private fun ListHeader(
    isListExpanded: Boolean,
    title: String,
    onClickVisibilityIcon: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable(
                    onClick = {
                        onClickVisibilityIcon(isListExpanded)
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = true,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                ),
            painter = painterResource(if (isListExpanded) BanklyIcons.ChevronUp else BanklyIcons.ChevronDown),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true, backgroundColor = PreviewColor.white)
@Composable
private fun ListHeaderPreview() {
    BanklyTheme {
        ListHeader(
            isListExpanded = true,
            title = stringResource(id = R.string.title_operational_banks),
            onClickVisibilityIcon = {},
        )
    }
}
