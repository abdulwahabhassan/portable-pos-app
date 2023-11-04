package com.bankly.feature.dashboard.ui.support

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.dashboard.model.SupportOption
import com.bankly.feature.dashboard.ui.component.SupportListItem

@Composable
internal fun SupportRoute(
    onBackPress: () -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
) {
    SupportScreen(
        onBackPress = onBackPress,
        onSupportOptionClick = onSupportOptionClick
    )
}

@Composable
private fun SupportScreen(
    onBackPress: () -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
) {
    BackHandler {
        onBackPress()
    }
    LazyColumn() {
        items(SupportOption.values()) { supportOption: SupportOption ->
            SupportListItem(
                supportOption = supportOption, onClick = {
                    onSupportOptionClick(supportOption)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SupportScreenPreview() {
    BanklyTheme {
        SupportScreen(
            onBackPress = {},
            onSupportOptionClick = {}
        )
    }
}
