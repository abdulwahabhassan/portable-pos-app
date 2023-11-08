package com.bankly.feature.logcomplaints.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.dashedBorder
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.logcomplaint.R

@Preview(showBackground = true)
@Composable
fun UploadFilePreview() {
    BanklyTheme {
        UploadFile(
            docName = "Failed POS receipt.jpg",
        ) {
        }
    }
}

@Composable
fun UploadFile(
    docName: String,
    isReadyForUpload: Boolean = false,
    isEnabled: Boolean = false,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .dashedBorder(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = MaterialTheme.shapes.medium,
                on = 4.dp,
                off = 4.dp,
            )
            .fillMaxWidth(),
        onClick = {
            if (isEnabled) onClick()
        },
    ) {
        Crossfade(targetState = isReadyForUpload, label = "") {
            if (it) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape,
                    ) {
                        Image(
                            modifier = Modifier.padding(16.dp),
                            painter = painterResource(id = BanklyIcons.Upload),
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = docName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            ),
                        )
                        Text(
                            text = stringResource(R.string.action_upload_file_here),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(24.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painterResource(id = BanklyIcons.Upload),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.action_upload_file_here),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
