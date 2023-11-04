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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyInfoText
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.AgentAccountDetails
import com.bankly.feature.paywithtransfer.R

@Composable
internal fun AccountDetailsView(
    isExpanded: Boolean,
    onExpandIconClick: (Boolean) -> Unit,
    isLoading: Boolean,
    accountDetails: AgentAccountDetails,
) {
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
                icon = if (isLoading.not()) (if (isExpanded) BanklyIcons.ChevronUp else BanklyIcons.ChevronDown) else BanklyIcons.ValidationInProgress,
                onClick = {
                    onExpandIconClick(isExpanded)
                },
                enabled = isLoading.not()
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
                        text = accountDetails.fundingAccountNumber,
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
                        text = accountDetails.name,
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
                        text = accountDetails.fundingSourceName,
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                BanklyInfoText(stringResource(R.string.msg_pop_up_box_hint))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun AccountDetailsPreview() {
    BanklyTheme {
        AccountDetailsView(
            isExpanded = true,
            onExpandIconClick = {},
            isLoading = false,
            accountDetails = AgentAccountDetails(
                fundingAccountNumber = "5010000017",
                name = "Josh Osazuwa",
                fundingSourceName = "Bankly MFB"
            )
        )
    }
}
