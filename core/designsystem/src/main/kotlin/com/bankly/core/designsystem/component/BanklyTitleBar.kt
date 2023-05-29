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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme

/**
 * @param onBackClick The action to be performed when navigation icon is clicked
 * If [onBackClick] is provided, navigation icon is shown otherwise hidden
 * @param currentPage The current page the user is on in a process flow such as an boarding flow
 * @param totalPage The total number of steps or screens in a single process flow
 *  [currentPage] must be less than [totalPage] otherwise they are not shown
 * @param subTitle The text that appears underneath [title] to provide extra information or hint
 */
@Composable
fun BanklyTitleBar(
    onBackClick: (() -> Unit)? = null,
    title: String,
    subTitle: AnnotatedString = buildAnnotatedString { append("") },
    currentPage: Int = 0,
    totalPage: Int = 0,
    isLoading: Boolean = false
) {
    val isValidPageCount by remember(currentPage, totalPage) {
        mutableStateOf(currentPage > 0 && totalPage > 0 && currentPage < totalPage)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                Box(modifier = Modifier.weight(1f)) {
                    BanklyBackButton(onClick = onBackClick)
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .weight(1f)
                ) {}
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 12.dp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            if (isValidPageCount) {
                Text(
                    text = "Step $currentPage/$totalPage",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.tertiary),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .width(90.dp)
                )
            } else {
                Box(modifier = Modifier.weight(1f))
            }

        }
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .height(2.dp)
                    .fillMaxWidth(),
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )
        } else {
            Spacer(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .height(2.dp)
            )
        }
        if (isValidPageCount) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 24.dp),
                progress = (currentPage.toFloat() / totalPage.toFloat()) * 1f,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )
        }


        if (subTitle.isNotEmpty()) {
            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.tertiary),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
            )
        }
    }
}

/**
 * Previews
 */
@Composable
@Preview(showBackground = true)
private fun BanklyTitleBarPreview1() {
    BanklyTheme {
        BanklyTitleBar(
            onBackClick = {},
            title = "Log In",
            subTitle = buildAnnotatedString { append("Fill in your sign in details to access your account") },
            currentPage = 1,
            totalPage = 2
        )

    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyTitleBarPreview2() {
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In"
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyTitleBarPreview3() {
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In",
            subTitle = buildAnnotatedString { append("Fill in your sign in details to access your account") }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyTitleBarPreview4() {
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In",
            subTitle = buildAnnotatedString { append("Fill in your sign in details to access your account") },
            currentPage = 3,
            totalPage = 10,
            isLoading = true
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyTitleBarPreview5() {
    BanklyTheme {
        BanklyTitleBar(
            onBackClick = { },
            title = "Log In",
            isLoading = true
        )
    }
}

