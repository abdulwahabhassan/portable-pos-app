package com.bankly.feature.authentication.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

@Composable
fun InputPassCodeScreen() {
    val phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    val isError by remember { mutableStateOf(false) }
    var ticks by remember { mutableStateOf(60) }
    val timerTrigger by remember { mutableStateOf(false) }
    var passCode by remember { mutableStateOf(List(6) { "" }) }

    LaunchedEffect(timerTrigger) {
        while (ticks > 0) {
            delay(1.seconds)
            ticks--
        }
        ticks = 60
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BanklyTitleBar(
                    onBackClick = {},
                    title = stringResource(R.string.title_recover_passcode),
                    subTitle = buildAnnotatedString {
                        append(
                            stringResource(R.string.msg_enter_passcode_sent_to_phone)
                        )
                        withStyle(
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 1.sp
                            ).toSpanStyle()
                        ) {
                            append("08167XXXX61")
                        }
                    },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BanklyPassCodeInputField(passCode = passCode, isError = isError)

            BanklyClickableText(text = if (ticks == 60) buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ).toSpanStyle()
                ) { append(stringResource(R.string.action_resend_code)) }
            } else buildAnnotatedString {
                append(stringResource(R.string.msg_resend_code_in))
                append(" ")
                withStyle(
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ).toSpanStyle()
                ) { append("${ticks}s") }
            }) {

            }
        }

        item {
            BanklyNumericKeyboard(onKeyPressed = { key ->
                when (key) {
                    PassCodeKey.DELETE -> {
                        val index = passCode.indexOfLast { it.isNotEmpty() }
                        if (index != -1) {
                            val newPassCode = passCode.toMutableList()
                            newPassCode[index] = ""
                            passCode = newPassCode
                        }
                    }

                    PassCodeKey.DONE -> {}
                    else -> {
                        val index = passCode.indexOfFirst { it.isEmpty() }
                        if (index != -1) {
                            val newPassCode = passCode.toMutableList()
                            newPassCode[index] = key.value
                            passCode = newPassCode
                        }
                    }
                }
            })
        }

    }


}

@Composable
@Preview(showBackground = true)
fun InputPassCodeScreenPreview() {
    BanklyTheme {
        InputPassCodeScreen()
    }
}