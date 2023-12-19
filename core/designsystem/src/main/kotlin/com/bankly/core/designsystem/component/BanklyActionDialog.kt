package com.bankly.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bankly.core.designsystem.R
import com.bankly.core.designsystem.theme.BanklyTheme

/**
 * [BanklyActionDialog] is used to display messages or feedback to user across the app.
 * When either of [positiveActionText] or [negativeActionText] is not null, their corresponding
 * action buttons are shown and their respective actions [positiveAction] or [negativeAction]
 * are called when either button is clicked
 */
@Deprecated("Deprecated in favour of BanklyCenterDialog")
@Composable
fun BanklyActionDialog(
    title: String,
    subtitle: String? = null,
    icon: Int? = R.drawable.ic_error_alert,
    positiveActionText: String? = null,
    positiveAction: () -> Unit = {},
    negativeActionText: String? = null,
    negativeAction: () -> Unit = {},
) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        icon?.let {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = icon),
                                contentDescription = "Error alert icon",
                                tint = Color.Unspecified,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        subtitle?.let {
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 10,
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            if (positiveActionText != null) {
                                BanklyOutlinedButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    text = positiveActionText,
                                    onClick = {
                                        showDialog = false
                                        positiveAction()
                                    },
                                )
                            }
                            if (negativeActionText != null) {
                                BanklyFilledButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    text = negativeActionText,
                                    onClick = {
                                        showDialog = false
                                        negativeAction()
                                    },
                                    backgroundColor = MaterialTheme.colorScheme.errorContainer,
                                    textColor = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ActionDialogPreview1() {
    BanklyTheme {
        BanklyActionDialog(
            title = stringResource(R.string.title_cancel_transaction),
            subtitle = stringResource(R.string.msg_are_you_sure_you_want_to_cancel),
            positiveActionText = stringResource(R.string.action_no),
            positiveAction = {
            },
            negativeActionText = stringResource(R.string.action_yes_cancel),
        ) {
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ActionDialogPreview2() {
    BanklyTheme {
        BanklyActionDialog(
            title = stringResource(R.string.msg_check_your_internet_connection),
            positiveActionText = "Okay",
            positiveAction = {
            },
        )
    }
}
