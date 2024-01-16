package com.bankly.core.common.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyExpandableList
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.model.entity.Bank

const val COMMERCIAL_BANKS = "Commercial Banks"

@Composable
fun BankSearchView(
    isBankListLoading: Boolean,
    bankList: List<com.bankly.core.model.entity.Bank>,
    onSelectBank: (com.bankly.core.model.entity.Bank) -> Unit,
    onCloseIconClick: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    val banks by remember(bankList, searchQuery) {
        mutableStateOf(bankList.filter { it.name.contains(searchQuery, true) })
    }
    if (isBankListLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
            modifier = Modifier.fillMaxSize(),
        ) {
            var isCommercialBankListExpanded by rememberSaveable { mutableStateOf(true) }
            var isMFBBankListExpanded by rememberSaveable { mutableStateOf(true) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(R.string.title_select_bank), style = MaterialTheme.typography.titleMedium)
                BanklyClickableIcon(
                    icon = BanklyIcons.Close,
                    onClick = onCloseIconClick,
                    shape = CircleShape,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            BanklySearchBar(
                modifier = Modifier,
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                    isCommercialBankListExpanded = true
                    isMFBBankListExpanded = true
                },
                searchPlaceholder = "Search bank name",
            )
            BanklyExpandableList(
                title = stringResource(R.string.title_commercial_banks),
                items = banks.filter { it.categoryId == 1 || it.categoryName == COMMERCIAL_BANKS },
                isExpanded = isCommercialBankListExpanded,
                onClickExpand = {
                    isCommercialBankListExpanded = !isCommercialBankListExpanded
                },
                onItemSelected = onSelectBank,
                transformItemToString = { bank: com.bankly.core.model.entity.Bank -> bank.name },
                drawItem = { item, onItemSelected, transformItemToString ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    onItemSelected(item)
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = true,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        BankListItem(bankName = transformItemToString(item))
                    }
                },
            )
            BanklyExpandableList(
                title = stringResource(R.string.title_other_banks),
                items = banks.filter { it.categoryId != 1 || it.categoryName != COMMERCIAL_BANKS },
                isExpanded = isMFBBankListExpanded,
                onClickExpand = {
                    isMFBBankListExpanded = !isMFBBankListExpanded
                },
                onItemSelected = onSelectBank,
                transformItemToString = { bank: com.bankly.core.model.entity.Bank -> bank.name },
                drawItem = { item, onItemSelected, transformItemToString ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    onItemSelected(item)
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = true,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        BankListItem(bankName = transformItemToString(item))
                    }
                },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun BankSearchViewPreview() {
    BanklyTheme {
        BankSearchView(
            isBankListLoading = false,
            bankList = emptyList(),
            onSelectBank = {},
            onCloseIconClick = {},
        )
    }
}

@Composable
private fun BankListItem(
    bankName: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = bankName,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun BankListItemPreview() {
    BanklyTheme {
        BankListItem("Bankly MFB")
    }
}
