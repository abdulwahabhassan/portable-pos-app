package com.bankly.core.common.ui.selectaccounttype

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyRowCheckBox
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.common.model.AccountType

@Composable
fun SelectAccountTypeRoute(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit
) {
    SelectAccountTypeScreen(
        onAccountSelected = onAccountSelected,
        onBackPress = onBackPress
    )
}


@Composable
@Preview(showBackground = true)
fun SelectAccountTypeScreenPreview() {
    BanklyTheme {
        SelectAccountTypeScreen(
            onAccountSelected = {},
            onBackPress = {}
        )
    }
}

@Composable
fun SelectAccountTypeScreen(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit
) {
    var selectedAccountType: AccountType? by remember { mutableStateOf(null) }
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_select_account_type),
                onCloseClick = onBackPress
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(AccountType.values()) { item: AccountType ->
                BanklyRowCheckBox(
                    isChecked = item == selectedAccountType,
                    onCheckedChange = {
                        selectedAccountType = item
                        onAccountSelected(item)
                    },
                    title = item.title
                )
            }
        }
    }
}
