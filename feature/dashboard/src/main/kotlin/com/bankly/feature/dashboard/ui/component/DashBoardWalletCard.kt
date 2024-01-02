package com.bankly.feature.dashboard.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bankly.core.common.util.Formatter.formatAmount
import com.bankly.core.common.util.copyToClipboard
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.util.BanklyDesignUtil
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.dashboard.R

@Composable
fun WalletCard(
    shouldShowWalletBalance: Boolean,
    onToggleWalletBalanceVisibility: (Boolean) -> Unit,
    accountNumber: String,
    bankName: String,
    currentBalance: Double,
    shouldShowVisibilityIcon: Boolean,
    isWalletBalanceLoading: Boolean,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .height(130.dp)
            .width(380.dp).run {
                if (isWalletBalanceLoading) {
                    background(
                        BanklyDesignUtil.shimmerBrush(),
                        shape = MaterialTheme.shapes.medium,
                    )
                } else {
                    background(
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium,
                    )
                }
            },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .weight(1.8f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Account Balance",
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = if (shouldShowWalletBalance) {
                            formatAmount(
                                value = currentBalance,
                                includeNairaSymbol = true,
                                addSpaceBetweenSymbolAndAmount = true,
                            )
                        } else {
                            "**********"
                        },
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                }
                if (shouldShowVisibilityIcon) {
                    BanklyClickableIcon(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(4.dp),
                        icon = if (shouldShowWalletBalance) BanklyIcons.VisibilityOff else BanklyIcons.VisibilityOn,
                        onClick = {
                            onToggleWalletBalanceVisibility(!shouldShowWalletBalance)
                        },
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (!isWalletBalanceLoading) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = BanklyIcons.Bank),
                        contentDescription = stringResource(R.string.desc_visibility_icon),
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$bankName: $accountNumber",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    BanklyClickableIcon(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(4.dp),
                        icon = BanklyIcons.Copy,
                        onClick = {
                            context.copyToClipboard(
                                accountNumber,
                                context.getString(R.string.msg_account_number_copied)
                            )
                        },
                    )
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun WalletCardPreview() {
    BanklyTheme {
        WalletCard(
            true,
            {},
            "5001987654",
            "Ampersand (BANKLY)",
            0.00,
            true,
            false,
        )
    }
}
