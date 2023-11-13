package com.bankly.feature.authentication.ui.unassignedterminal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyOutlinedButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
internal fun UnassignedTerminalRoute(
    onGoToBackPress: () -> Unit,
    onContactSupportPress: () -> Unit
) {
    UnassignedTerminalScreen(
        onGoToBackPress = onGoToBackPress,
        onContactSupportPress = onContactSupportPress
    )
}

@Composable
internal fun UnassignedTerminalScreen(
    onGoToBackPress: () -> Unit,
    onContactSupportPress: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
        content = {
            item {
                Box(modifier = Modifier.height(56.dp))
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 120.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Icon(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = BanklyIcons.WarningAlert),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(com.bankly.feature.authentication.R.string.title_unassigned_pos_terminal),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(com.bankly.feature.authentication.R.string.msg_please_contact_support_to_assign_the_terminal_to_your_account),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                ) {
                    BanklyFilledButton(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth(),
                        text = stringResource(com.bankly.feature.authentication.R.string.action_contact_support),
                        onClick = onContactSupportPress,
                        isEnabled = true,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    BanklyOutlinedButton(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth(),
                        text = stringResource(com.bankly.feature.authentication.R.string.action_go_back),
                        onClick = onGoToBackPress,
                        isEnabled = true,
                    )
                }
            }
        })
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun UnassignedTerminalScreenPreview() {
    BanklyTheme {
        UnassignedTerminalScreen(
            onGoToBackPress = {},
            onContactSupportPress = {}
        )
    }
}
