package com.bankly.feature.paywithtransfer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.paywithtransfer.R

@Composable
internal fun AccountDetailsView(
    onBackPress: () -> Unit,
) {
    var isExpanded: Boolean by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = BanklyIcons.Bank),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
                Text(
                    text = stringResource(R.string.title_account_details),
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                )
            }
            BanklyClickableIcon(
                icon = if (isExpanded) BanklyIcons.Chevron_Up else BanklyIcons.Chevron_Down,
                onClick = {
                    isExpanded = !isExpanded
                },
            )
        }
        AnimatedVisibility(visible = isExpanded) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.title_account_number),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                    Text(
                        text = "5010000017",
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.title_account_name),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                    Text(
                        text = "Josh Osazuwa",
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.title_bank_name),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                    Text(
                        text = "Bankly MFB",
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = BanklyIcons.Info),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                    Text(
                        text = stringResource(R.string.msg_pop_up_box_hint),
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AccountDetailsPreview() {
    BanklyTheme {
        AccountDetailsView(onBackPress = {})
    }
}