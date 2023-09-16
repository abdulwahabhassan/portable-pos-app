package com.bankly.core.common.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyExpandableList
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.entity.Bank

@Composable
fun BankSearchView(
    isBankListLoading: Boolean,
    bankList: List<Bank>,
    onSelectBank: (Bank) -> Unit,
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
                transformItemToString = { bank: Bank -> bank.name },
            )
            BanklyExpandableList(
                title = stringResource(R.string.title_other_banks),
                items = banks.filter { it.categoryId != 1 || it.categoryName != COMMERCIAL_BANKS },
                isExpanded = isMFBBankListExpanded,
                onClickExpand = {
                    isMFBBankListExpanded = !isMFBBankListExpanded
                },
                onItemSelected = onSelectBank,
                transformItemToString = { bank: Bank -> bank.name },
            )
        }
    }
}

const val COMMERCIAL_BANKS = "Commercial Banks"

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
internal fun BankSearchViewPreview() {
    BanklyTheme {
        BankSearchView(
            isBankListLoading = false,
            bankList = emptyList(),
            onSelectBank = {},
        )
    }
}
