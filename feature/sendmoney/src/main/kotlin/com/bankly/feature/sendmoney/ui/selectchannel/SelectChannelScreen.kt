package com.bankly.feature.sendmoney.ui.selectchannel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.sendmoney.R
import com.bankly.core.common.model.SendMoneyChannel

@Composable
internal fun SelectChannelRoute(
    onBackPress: () -> Unit,
    onSelectChannel: (SendMoneyChannel) -> Unit
) {
    SelectChannelScreen(
        onBackPress = onBackPress,
        onSelectChannel = onSelectChannel
    )
}

@Composable
private fun SelectChannelScreen(
    onBackPress: () -> Unit,
    onSelectChannel: (SendMoneyChannel) -> Unit
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.sendmoney),
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.title_please_select_transaction_channel))
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(SendMoneyChannel.values()) { item ->
                SendMoneyChannel(
                    destination = item,
                    onClick = { sendMoneyChannel: SendMoneyChannel ->
                        onSelectChannel(sendMoneyChannel)
                    }
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun SelectChannelScreenPreview() {
    BanklyTheme {
        SelectChannelScreen({}, {})
    }
}

@Composable
private fun SendMoneyChannel(
    destination: SendMoneyChannel,
    onClick: (SendMoneyChannel) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    onClick(destination)
                },
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = destination.icon),
            contentDescription = null
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = destination.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = destination.description,
                style = MaterialTheme.typography.bodyMedium,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SendMoneyChannelPreview() {
    BanklyTheme {
        SendMoneyChannel(
            SendMoneyChannel.BANKLY_TO_BANKLY
        ) {}
    }
}