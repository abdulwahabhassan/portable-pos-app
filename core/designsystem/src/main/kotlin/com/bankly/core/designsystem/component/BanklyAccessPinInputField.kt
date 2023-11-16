package com.bankly.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.R
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
fun BanklyAccessPinInputField(
    passCode: List<String>,
    isError: Boolean = false,
    pinErrorMessage: String = stringResource(R.string.msg_incorrect_access_pin)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.weight(1.5f))
            passCode.forEachIndexed { index, s ->
                if (index != 0) Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = BanklyIcons.PassCodeDigitIndicator),
                        contentDescription = null,
                        tint = if (s.isNotEmpty()) if (isError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Unspecified
                        }
                    )
                }
                if (index != passCode.lastIndex) Spacer(modifier = Modifier.width(8.dp))
            }
            Box(Modifier.weight(1.5f))
        }
        Text(
            text = if (isError) pinErrorMessage else "",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}

@Preview(showBackground = true, backgroundColor = PreviewColor.white)
@Composable
private fun BanklyAccessCodeInputFieldPreview1() {
    BanklyTheme {
        BanklyAccessPinInputField(
            listOf("1", "2", "", "", "", ""),
        )
    }
}

@Preview(showBackground = true, backgroundColor = PreviewColor.white)
@Composable
private fun BanklyAccessCodeInputFieldPreview2() {
    BanklyTheme {
        BanklyAccessPinInputField(
            listOf("1", "2", "", "", "", "", "", ""),
            isError = true,
        )
    }
}

@Preview(showBackground = true, backgroundColor = PreviewColor.white)
@Composable
private fun BanklyAccessCodeInputFieldPreview3() {
    BanklyTheme {
        BanklyAccessPinInputField(
            listOf("1", "2", "", ""),
        )
    }
}
