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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun BanklyCenterDialog(
    title: String,
    subtitle: String? = null,
    icon: Int? = null,
    positiveActionText: String? = null,
    positiveAction: () -> Unit = {},
    negativeActionText: String? = null,
    negativeAction: () -> Unit = {},
    showDialog: Boolean,
    onDismissDialog: () -> Unit,
    showCloseIcon: Boolean = false,
    extraContent: @Composable (() -> Unit)? = null,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissDialog,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
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
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (showCloseIcon) {
                                BanklyClickableIcon(
                                    Modifier.align(Alignment.TopEnd),
                                    icon = BanklyIcons.Close,
                                    onClick = onDismissDialog,
                                    shape = CircleShape,
                                )
                            }
                            icon?.let {
                                Column {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Icon(
                                        modifier = Modifier.size(40.dp),
                                        painter = painterResource(id = icon),
                                        contentDescription = "Error alert icon",
                                        tint = Color.Unspecified,
                                    )
                                }
                            }
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
                                        onDismissDialog()
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
                                        onDismissDialog()
                                        negativeAction()
                                    },
                                    backgroundColor = MaterialTheme.colorScheme.errorContainer,
                                    textColor = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                        if (extraContent != null) {
                            extraContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BanklyCenterDialogPreview1() {
    BanklyTheme {
        val context = LocalContext.current
        BanklyCenterDialog(
            title = stringResource(R.string.title_cancel_transaction),
            subtitle = stringResource(R.string.msg_are_you_sure_you_want_to_cancel),
            positiveActionText = stringResource(R.string.action_no),
            positiveAction = {
            },
            negativeActionText = stringResource(R.string.action_yes_cancel),
            showDialog = false,
            onDismissDialog = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BanklyCenterDialogPreview2() {
    BanklyTheme {
        val context = LocalContext.current
        BanklyCenterDialog(
            title = stringResource(R.string.msg_check_your_internet_connection),
            positiveActionText = "Okay",
            icon = BanklyIcons.ErrorAlert,
            positiveAction = {
            },
            showDialog = false,
            onDismissDialog = {},
        )
    }
}
