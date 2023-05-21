package com.bankly.feature.authentication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Composable
fun SuccessfulScreen(
    message: String,
    subMessage: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 120.dp)
                .fillMaxSize()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = BanklyIcons.Successful),
                contentDescription = "Successful Icon"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.tertiary)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = subMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.tertiary)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BanklyFilledButton(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(), buttonText, onClick, true
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SuccessfulScreenPreview() {
    BanklyTheme {
        SuccessfulScreen(
            message = "Passcode Reset Successfully",
            subMessage = "You have successfully reset your passcode. Login to continue",
            buttonText = stringResource(R.string.action_back_to_log_in),
            onClick = {}
        )
    }


}