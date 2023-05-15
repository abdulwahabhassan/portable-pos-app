package com.bankly.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    subTitle: String = "",
    currentPage: Int = 0,
    totalPage: Int = 0
) {
    val isValidPageCount by remember(currentPage, totalPage) {
        mutableStateOf(currentPage > 0 && totalPage > 0 && currentPage < totalPage)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                Box(modifier = Modifier.weight(1f)) {
                    BanklyBackButton(onClick = onBackClick)
                }
            } else {
                Box(modifier = Modifier.weight(1f))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.onTertiary),
                modifier = Modifier
                    .weight(2f)
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
                    style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onTertiary),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .width(90.dp)
                )
            } else {
                Box(modifier = Modifier.weight(1f))
            }

        }
        if (isValidPageCount) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 24.dp),
                progress = (currentPage.toFloat() / totalPage.toFloat()) * 1f,
                strokeCap = StrokeCap.Round
            )
        }
        if (subTitle.isNotEmpty()) {
            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onTertiary),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp)
            )
        }
    }
}

/**
 * Previews
 */
@Composable
@Preview(showBackground = true)
internal fun BanklyTitleBarPreview1() {
    /**
     * Preview with back button
     */
    BanklyTheme {
        BanklyTitleBar(
            onBackClick = {},
            title = "Log In",
            subTitle = "Fill in your sign in details to access your account",
            currentPage = 1,
            totalPage = 2
        )

    }
}

@Composable
@Preview(showBackground = true)
internal fun BanklyTitleBarPreview2() {
    /**
     * Preview without back button
     */
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In"
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun BanklyTitleBarPreview3() {
    /**
     * Preview without back button
     */
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In",
            subTitle = "Fill in your sign in details to access your account"
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun BanklyTitleBarPreview4() {
    /**
     * Preview without back button
     */
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In",
            currentPage = 3,
            totalPage = 10
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun BanklyTitleBarPreview5() {
    /**
     * Preview without back button
     */
    BanklyTheme {
        BanklyTitleBar(
            onBackClick = { },
            title = "Log In",
        )
    }
}

