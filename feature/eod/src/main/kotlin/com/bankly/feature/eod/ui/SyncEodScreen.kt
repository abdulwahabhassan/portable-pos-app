package com.bankly.feature.eod.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.eod.R
import com.bankly.feature.eod.ui.component.SyncEodItemCard


@Composable
internal fun SyncEodRoute(
    onBackPress: () -> Unit,
) {
    SyncEodScreen(
        onBackPress = onBackPress,
        processedAmount = "0.00",
        settledAmount = "0.00",
        isLoading = false
    )
}

@Composable
internal fun SyncEodScreen(
    onBackPress: () -> Unit,
    processedAmount: String,
    settledAmount: String,
    isLoading: Boolean,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                isLoading = isLoading,
                onBackPress = onBackPress,
                title = stringResource(id = R.string.sync_od)
            )
        }
    ) { paddingValues: PaddingValues ->
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Syncing EOD, please wait",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            SyncEodItemCard(
                                processedAmount,
                                stringResource(R.string.msg_processed),
                                MaterialTheme.colorScheme.primary,
                                icon = BanklyIcons.SyncProcessed
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            SyncEodItemCard(
                                settledAmount,
                                stringResource(R.string.msg_settled),
                                BanklySuccessColor.successColor,
                                icon = BanklyIcons.SyncSettled
                            )
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            .clip(CircleShape)
                            .clickable(
                                onClick = {

                                },
                                role = Role.Button,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = true,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            )
                            .padding(vertical = 6.dp, horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tap to sync EOD",
                            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Sync EOD to get updated transaction report",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SyncEodScreenPreview() {
    BanklyTheme {
        SyncEodScreen(
            onBackPress = {},
            processedAmount = "0.00",
            settledAmount = "0.00",
            isLoading = false
        )
    }
}