package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme


@Composable
fun BanklyPassCodeInputField(
    passCode: List<String>,
    isError: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        passCode.forEachIndexed { index, s ->
            val isFocused by remember(passCode) {
                mutableStateOf(passCode.indexOfFirst { it.isEmpty() } == index)
            }
            if (index != 0) Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier

                    .height(68.dp)
                    .background(
                        color = if (isError) MaterialTheme.colorScheme.errorContainer else
                            MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    )
                    .border(
                        width = 1.dp,
                        color = if (isFocused) if (isError) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = s,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                        .copy(
                            color = if (isError) MaterialTheme.colorScheme.onErrorContainer
                            else MaterialTheme.colorScheme.tertiary
                        ),
                )
            }
            if (index != passCode.lastIndex) Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BanklyPassCodeInputFieldPreview1() {
    BanklyTheme {
        BanklyPassCodeInputField(
            listOf("1", "2", "", "", "", "")
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BanklyPassCodeInputFieldPreview2() {
    BanklyTheme {
        BanklyPassCodeInputField(
            listOf("1", "2", "", "", "", "", "", ""),
            isError = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BanklyPassCodeInputFieldPreview3() {
    BanklyTheme {
        BanklyPassCodeInputField(
            listOf("1", "2", "", "")
        )
    }
}



