package com.bankly.feature.logcomplaints.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.util.copyToClipboard
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.logcomplaint.R

@Composable
internal fun LoggedComplaintRoute(
    onBackPress: () -> Unit,
    onGoToHome: () -> Unit,
    complaintId: String,
) {
    LoggedComplaintScreen(
        onBackPress = onBackPress,
        onGoToHome = onGoToHome,
        complaintId = complaintId,
    )
}

@Composable
internal fun LoggedComplaintScreen(
    onBackPress: () -> Unit,
    onGoToHome: () -> Unit,
    complaintId: String,
) {
    val context = LocalContext.current
    BackHandler {
        onBackPress()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = 24.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(modifier = Modifier.height(56.dp))
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = BanklyIcons.Successful),
                        contentDescription = "Successful Icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.msg_complaint_logged),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Medium,
                        ),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.msg_complaint_logged_successfully),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.tertiary,
                        ),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    BanklyClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                ).toSpanStyle(),
                            ) {
                                append("ID: 38924y4920ID")
                            }
                        },
                        onClick = { },
                        backgroundShape = MaterialTheme.shapes.small,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        trailingIcon = {
                            BanklyClickableIcon(
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(4.dp),
                                icon = BanklyIcons.Copy,
                                onClick = {
                                    context.copyToClipboard(complaintId)
                                },
                                color = MaterialTheme.colorScheme.primary,
                            )
                        },
                    )
                }
            }
            item {
                Column {
                    Spacer(modifier = Modifier.height(32.dp))
                    BanklyFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Go to Home",
                        onClick = onGoToHome,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun NewComplaintScreenPreview() {
    BanklyTheme {
        LoggedComplaintScreen(
            onBackPress = {},
            onGoToHome = {},
            complaintId = "ID: 38924y4920ID",
        )
    }
}
