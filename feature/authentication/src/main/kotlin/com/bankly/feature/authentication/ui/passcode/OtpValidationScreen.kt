package com.bankly.feature.authentication.ui.passcode

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Immutable
data class OtpValidationScreenUiState(
    val otp: List<String> = List(6) { "" },
    val phoneNumber: String = "",
    val isOtpError: Boolean = false,
    val otpFeedBack: String = "",
    val ticks: Int = 60,
    val showActionDialog: Boolean = false
) {
    val isDoneButtonEnabled: Boolean
        get() = otp.all { digit: String -> digit.isNotEmpty() } && !isOtpError
}

@Composable
fun rememberOtpValidationScreenUiState(phoneNumber: String): MutableState<OtpValidationScreenUiState> =
    remember(phoneNumber) { mutableStateOf(OtpValidationScreenUiState(phoneNumber = phoneNumber)) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OtpValidationScreen(
    viewModel: OtpValidationViewModel = hiltViewModel(),
    phoneNumber: String,
    onOtpValidationSuccess: (phoneNumber: String, otp: String) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val otpValidationState by viewModel.state.collectAsStateWithLifecycle()
    var otpValidationScreenUiState by rememberOtpValidationScreenUiState(phoneNumber = phoneNumber)
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        otpValidationScreenUiState = otpValidationScreenUiState.copy(showActionDialog = true)
    }

    if (otpValidationScreenUiState.showActionDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.title_confirm_action),
            subtitle = stringResource(R.string.msg_are_you_sure_you_do_not_want_to_continue_re_setting_your_passcode),
            positiveActionText = stringResource(R.string.action_yes),
            positiveAction = {
                onBackButtonClick()
            },
            negativeActionText = stringResource(R.string.action_no),
            negativeAction = {
                otpValidationScreenUiState = otpValidationScreenUiState.copy(showActionDialog = false)
            })
    }

    @Composable
    fun startTimer() {
        LaunchedEffect(key1 = Unit, block = {
            coroutineScope.launch {
                while (otpValidationScreenUiState.ticks > 0) {
                    delay(1.seconds)
                    otpValidationScreenUiState =
                        otpValidationScreenUiState.copy(ticks = otpValidationScreenUiState.ticks - 1)
                }
                otpValidationScreenUiState = otpValidationScreenUiState.copy(ticks = 60)
            }
        })
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackClick = {
                    otpValidationScreenUiState = otpValidationScreenUiState.copy(showActionDialog = true)
                },
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
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
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
                        viewModel.sendEvent(OtpValidationUiEvent.ResendOtp(otpValidationScreenUiState.phoneNumber))
                    },
                    isEnabled = otpValidationState !is OtpValidationState.Loading && otpValidationScreenUiState.ticks == 60
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
                                    OtpValidationUiEvent.ValidateOtp(
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
    }

    startTimer()

    when (val state = otpValidationState) {
        is OtpValidationState.Initial -> {}
        is OtpValidationState.Loading -> {}
        is OtpValidationState.Error -> {
            Log.d("otp error debug", "show dialog")
            BanklyActionDialog(
                title = stringResource(R.string.title_otp_validation_error),
                subtitle = state.errorMessage,
                positiveActionText = stringResource(R.string.action_okay),
                positiveAction = {
                    viewModel.sendEvent(OtpValidationUiEvent.ResetState)
                })
        }

        is OtpValidationState.OtpValidationSuccess -> onOtpValidationSuccess(
            otpValidationScreenUiState.phoneNumber,
            otpValidationScreenUiState.otp.joinToString("")
        )

        OtpValidationState.ResendOtpSuccess -> {
            startTimer()
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun OtpValidationScreenPreview() {
    BanklyTheme {
        OtpValidationScreen(
            phoneNumber = "08167039661",
            onOtpValidationSuccess = { _, _ -> },
            onBackButtonClick = {}
        )
    }
}