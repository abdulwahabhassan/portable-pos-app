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
import androidx.compose.ui.platform.LocalContext
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
    onCancelPress: () -> Unit,
) {
    SelectAccountTypeScreen(
        onAccountSelected = onAccountSelected,
        onBackPress = onBackPress,
        onCancelPress = onCancelPress,
    )
}

@Composable
@Preview(showBackground = true)
fun SelectAccountTypeScreenPreview() {
    BanklyTheme {
        SelectAccountTypeScreen(
            onAccountSelected = {},
            onBackPress = {},
            onCancelPress = {},
        )
    }
}

@Composable
fun SelectAccountTypeScreen(
    onAccountSelected: (AccountType) -> Unit,
    onBackPress: () -> Unit,
    onCancelPress: () -> Unit,
) {
    val context = LocalContext.current
    var selectedAccountType: AccountType? by remember { mutableStateOf(null) }
    var showActionDialog by rememberSaveable { mutableStateOf(false) }
    var actionTitle by rememberSaveable { mutableStateOf("") }
    var actionMessage by rememberSaveable { mutableStateOf("") }

    if (showActionDialog) {
        BanklyActionDialog(
            title = actionTitle,
            subtitle = actionMessage,
            positiveActionText = context.getString(R.string.action_no),
            positiveAction = {
                showActionDialog = false
            },
            negativeActionText = context.getString(R.string.action_yes),
            negativeAction = {
                showActionDialog = false
                onCancelPress()
            },
        )
    }

    fun triggerCancelDialog() {
        actionTitle = context.getString(R.string.title_cancel_transaction)
        actionMessage = context.getString(R.string.msg_are_you_sure_you_want_to_cancel)
        showActionDialog = true
    }

    BackHandler {
        triggerCancelDialog()
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_select_account_type),
                onTrailingIconClick = {
                    triggerCancelDialog()
                },
            )
        },
    ) { padding ->
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
                    },
                    title = item.title,
                )
            }
        }
    }
}
