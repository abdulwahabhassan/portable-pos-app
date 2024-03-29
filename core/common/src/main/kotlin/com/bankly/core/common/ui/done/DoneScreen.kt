package com.bankly.core.common.ui.done

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
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
fun DoneRoute(
    title: String,
    message: String,
    onDoneClick: () -> Unit,
) {
    DoneScreen(
        title = title,
        message = message,
        onDoneClick = onDoneClick,
    )
}

@Composable
fun DoneScreen(
    title: String,
    message: String,
    onDoneClick: () -> Unit,
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
                        painter = painterResource(id = BanklyIcons.Successful),
                        contentDescription = "Successful Icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
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
                        text = stringResource(R.string.action_done),
                        onClick = onDoneClick,
                        isEnabled = true,
                    )
                }
            }
        },
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
fun DoneScreenPreview() {
    BanklyTheme {
        DoneScreen(
            title = stringResource(R.string.msg_transaction_successful),
            message = "",
            onDoneClick = {},
        )
    }
}
