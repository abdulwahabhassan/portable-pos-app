package com.bankly.core.common.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.component.BanklyExpandableList
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun BankSearchView(
    bankList: List<String>,
    onSelectBank: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    val banks by remember(bankList, searchQuery) {
        mutableStateOf(bankList.filter { it.contains(searchQuery, true) })
    }
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
            searchPlaceholder = "Search bank name"
        )
        BanklyExpandableList(
            title = "Commercial banks",
            listItems = banks.filter { !it.contains("MFB", true) },
            isExpanded = isCommercialBankListExpanded,
            onClickExpand = {
                isCommercialBankListExpanded = !isCommercialBankListExpanded
            },
            onItemSelected = onSelectBank
        )
        BanklyExpandableList(
            title = "Other banks",
            listItems = banks.filter { it.contains("MFB", true) },
            isExpanded = isMFBBankListExpanded,
            onClickExpand = {
                isMFBBankListExpanded = !isMFBBankListExpanded
            },
            onItemSelected = onSelectBank
        )
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
internal fun BankSearchViewPreview() {
    BanklyTheme {
        BankSearchView(
            bankList = listOf("Kuda MFB", "Carbon MFB", "GTB Bank", "First Bank of Nigeria (FBN)"),
            onSelectBank = {}
        )
    }
}
