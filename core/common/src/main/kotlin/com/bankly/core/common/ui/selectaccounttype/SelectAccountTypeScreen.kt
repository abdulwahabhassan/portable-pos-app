package com.bankly.core.common.ui.selectaccounttype

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.common.model.AccountType
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyRowCheckBox
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun SelectAccountTypeRoute(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit,
) {
    SelectAccountTypeScreen(
        onAccountSelected = onAccountSelected,
        onBackPress = onBackPress,
    )
}

@Composable
@Preview(showBackground = true)
fun SelectAccountTypeScreenPreview() {
    BanklyTheme {
        SelectAccountTypeScreen(
            onAccountSelected = {},
            onBackPress = {},
        )
    }
}

@Composable
fun SelectAccountTypeScreen(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit,
) {
    var selectedAccountType: AccountType? by remember { mutableStateOf(null) }
    var isLoading: Boolean by remember { mutableStateOf(false) }
    var showActionDialog by rememberSaveable { mutableStateOf(false) }
    var actionTitle by rememberSaveable { mutableStateOf("") }
    var actionMessage by rememberSaveable { mutableStateOf("") }

    if (showActionDialog) {
        BanklyActionDialog(
            title = actionTitle,
            subtitle = actionMessage,
            positiveActionText = "No",
            positiveAction = {
                showActionDialog = false
            },
            negativeActionText = "Yes",
            negativeAction = {
                showActionDialog = false
                onBackPress()
            },
        )
    }

    fun triggerCancelDialog() {
        if (isLoading) {
            actionTitle = "Cancel Transaction?"
            actionMessage = "Are you sure you want to cancel?"
            showActionDialog = true
        } else {
            onBackPress()
        }
    }

    BackHandler {
        triggerCancelDialog()
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = {
                    triggerCancelDialog()
                },
                title = if (isLoading) "" else stringResource(R.string.title_select_account_type),
                onCloseClick = {
                    triggerCancelDialog()
                },
            )
        },
    ) { padding ->
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(50.dp)
                            .size(100.dp),
                        strokeWidth = 12.dp,
                        trackColor = MaterialTheme.colorScheme.primaryContainer,
                        strokeCap = StrokeCap.Round,
                    )
                    Text(
                        modifier = Modifier
                            .padding(24.dp),
                        text = "Insert card and do not remove until transaction is complete ..",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(AccountType.values()) { item: AccountType ->
                    BanklyRowCheckBox(
                        isChecked = item == selectedAccountType,
                        onCheckedChange = {
                            selectedAccountType = item
                            onAccountSelected(item)
                            isLoading = true
                        },
                        title = item.title,
                    )
                }
            }
        }
    }
}
