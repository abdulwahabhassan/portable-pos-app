package com.bankly.feature.authentication.ui.validateotp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.bankly.core.common.model.State
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Composable
internal fun OtpValidationRoute(
    viewModel: OtpValidationViewModel = hiltViewModel(),
    phoneNumber: String,
    onOtpValidationSuccess: (phoneNumber: String, otp: String) -> Unit,
    onBackPress: () -> Unit
) {
    val screenState by viewModel.state.collectAsStateWithLifecycle()
    OtpValidationScreen(
        screenState = screenState,
        phoneNumber = phoneNumber,
        onOtpValidationSuccess = onOtpValidationSuccess,
        onBackPress = onBackPress,
        onUiEvent = { uiEvent: OtpValidationScreenEvent -> viewModel.sendEvent(uiEvent) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OtpValidationScreen(
    screenState: OtpValidationScreenState,
    phoneNumber: String,
    onOtpValidationSuccess: (phoneNumber: String, otp: String) -> Unit,
    onBackPress: () -> Unit,
    onUiEvent: (OtpValidationScreenEvent) -> Unit
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
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
                            phoneNumber.replaceRange(
                                startIndex = 4,
                                endIndex = 9,
                                replacement = "XXXXX"
                            )
                        )
                    }
                },
                isLoading = screenState.isLoading
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))

                BanklyPassCodeInputField(
                    passCode = screenState.otp
                )

                BanklyClickableText(
                    text = if (screenState.ticks == 0) buildAnnotatedString {
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
                        ) { append("${screenState.ticks}s") }
                    },
                    onClick = {
                        onUiEvent(OtpValidationScreenEvent.OnResendOtpClick(phoneNumber))
                    },
                    isEnabled = screenState.isResendCodeTextButtonEnabled
                )
            }

            item {
                BanklyNumericKeyboard(
                    onKeyPressed = { key ->
                        when (key) {
                            PassCodeKey.DELETE -> {
                                val index =
                                    screenState.otp.indexOfLast { it.isNotEmpty() }
                                if (index != -1) {
                                    val newOtp = screenState.otp.toMutableList()
                                    newOtp[index] = ""
                                    onUiEvent(OtpValidationScreenEvent.OnEnterOtp(otp = newOtp))
                                }
                            }

                            PassCodeKey.DONE -> {
                                onUiEvent(
                                    OtpValidationScreenEvent.OnDoneClick(
                                        screenState.otp.joinToString(""),
                                        phoneNumber
                                    )
                                )
                            }

                            else -> {
                                val index =
                                    screenState.otp.indexOfFirst { it.isEmpty() }
                                if (index != -1) {
                                    val newOtp = screenState.otp.toMutableList()
                                    newOtp[index] = key.value
                                    onUiEvent(OtpValidationScreenEvent.OnEnterOtp(otp = newOtp))
                                }
                            }
                        }
                    },
                    isKeyPadEnabled = screenState.isKeyPadEnabled,
                    isDoneKeyEnabled = screenState.isDoneButtonEnabled
                )
            }
        }
    }

    when (val state = screenState.otpValidationState) {
        is State.Initial, is State.Loading -> {}
        is State.Error -> {
            BanklyActionDialog(
                title = stringResource(R.string.title_otp_validation_error),
                subtitle = state.message,
                positiveActionText = stringResource(R.string.action_okay)
            )
        }

        is State.Success -> {
            onUiEvent(OtpValidationScreenEvent.OnExit)
            onOtpValidationSuccess(
                phoneNumber,
                screenState.otp.joinToString("")
            )
        }
    }

    when (val state = screenState.resendOtpState) {
        is State.Initial, is State.Loading, is State.Success -> {}
        is State.Error -> {
            BanklyActionDialog(
                title = stringResource(R.string.title_resend_otp_error),
                subtitle = state.message,
                positiveActionText = stringResource(R.string.action_okay)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun OtpValidationScreenPreview() {
    BanklyTheme {
        OtpValidationScreen(
            screenState = OtpValidationScreenState(),
            phoneNumber = "08167039661",
            onOtpValidationSuccess = { _, _ -> },
            onBackPress = {},
            onUiEvent = {}
        )
    }
}