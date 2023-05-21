package com.bankly.feature.authentication.ui.recover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.ActionDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

@Immutable
data class OtpValidationScreenUiState(
    val otp: List<String> = List(6) { "" },
    val phoneNumber: String = "",
    val isOtpError: Boolean = false,
    val otpFeedBack: String = "",
    val ticks: Int = 60,
    val timerTrigger: Boolean = false,

    ) {
    val isDoneButtonEnabled: Boolean
        get() = otp.all { digit: String -> digit.isNotEmpty() } && !isOtpError
}

@Composable
fun rememberOtpValidationScreenUiState(phoneNumber: String): MutableState<OtpValidationScreenUiState> =
    remember(phoneNumber) { mutableStateOf(OtpValidationScreenUiState(phoneNumber = phoneNumber)) }

@Composable
fun OtpValidationScreen(
    viewModel: OtpValidationViewModel = hiltViewModel(),
    phoneNumber: String,
    onOtpValidationSuccess: (String) -> Unit
) {
    val otpValidationState by viewModel.uiState.collectAsStateWithLifecycle()
    var otpValidationScreenUiState by rememberOtpValidationScreenUiState(phoneNumber = phoneNumber)

    LaunchedEffect(otpValidationScreenUiState.timerTrigger) {
        while (otpValidationScreenUiState.ticks > 0) {
            delay(1.seconds)
            otpValidationScreenUiState =
                otpValidationScreenUiState.copy(ticks = otpValidationScreenUiState.ticks - 1)
        }
        otpValidationScreenUiState = otpValidationScreenUiState.copy(ticks = 60)
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
                            append(
                                otpValidationScreenUiState.phoneNumber.replaceRange(
                                    startIndex = 4,
                                    endIndex = 9,
                                    replacement = "XXXXX"
                                )
                            )
                        }
                    },
                    isLoading = otpValidationState is OtpValidationState.Loading
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BanklyPassCodeInputField(
                passCode = otpValidationScreenUiState.otp,
                isError = otpValidationScreenUiState.isOtpError
            )

            BanklyClickableText(
                text = if (otpValidationScreenUiState.ticks == 60) buildAnnotatedString {
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
                    ) { append("${otpValidationScreenUiState.ticks}s") }
                },
                onClick = {

                },
                isEnabled = otpValidationState !is OtpValidationState.Loading
            )
        }

        item {
            BanklyNumericKeyboard(
                onKeyPressed = { key ->
                    when (key) {
                        PassCodeKey.DELETE -> {
                            val index =
                                otpValidationScreenUiState.otp.indexOfLast { it.isNotEmpty() }
                            if (index != -1) {
                                val newOtp = otpValidationScreenUiState.otp.toMutableList()
                                newOtp[index] = ""
                                otpValidationScreenUiState =
                                    otpValidationScreenUiState.copy(otp = newOtp)
                            }
                        }

                        PassCodeKey.DONE -> {
                            viewModel.sendEvent(
                                OtpValidationUiEvent.OtpValidation(
                                    otpValidationScreenUiState.otp.joinToString(""),
                                    otpValidationScreenUiState.phoneNumber
                                )
                            )
                        }

                        else -> {
                            val index = otpValidationScreenUiState.otp.indexOfFirst { it.isEmpty() }
                            if (index != -1) {
                                val newOtp = otpValidationScreenUiState.otp.toMutableList()
                                newOtp[index] = key.value
                                otpValidationScreenUiState =
                                    otpValidationScreenUiState.copy(otp = newOtp)
                            }
                        }
                    }
                },
                isKeyPadEnabled = otpValidationState !is OtpValidationState.Loading,
                isDoneKeyEnabled = otpValidationState !is OtpValidationState.Loading && otpValidationScreenUiState.isDoneButtonEnabled
            )
        }
    }

    when (val state = otpValidationState) {
        is OtpValidationState.Initial -> {}
        is OtpValidationState.Loading -> {}
        is OtpValidationState.Error -> {
            ActionDialog(
                title = "OTP validation error",
                subtitle = state.errorMessage,
                positiveActionText = stringResource(R.string.action_okay),
                positiveAction = {
                    viewModel.sendEvent(OtpValidationUiEvent.ResetState)
                })
        }

        is OtpValidationState.Success -> onOtpValidationSuccess(otpValidationScreenUiState.phoneNumber)

    }

}

@Composable
@Preview(showBackground = true)
fun OtpValidationScreenPreview() {
    BanklyTheme {
        OtpValidationScreen(
            phoneNumber = "08167039661",
            onOtpValidationSuccess = {}
        )
    }
}