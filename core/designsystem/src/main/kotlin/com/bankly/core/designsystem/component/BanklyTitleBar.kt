package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

/**
 * @param onBackPress The action to be performed when navigation icon is clicked
 * If [onBackPress] is provided, navigation icon is shown otherwise hidden
 * @param currentPage The current page the user is on in a process flow such as an boarding flow
 * @param totalPage The total number of steps or screens in a single process flow
 *  [currentPage] must be less than [totalPage] otherwise they are not shown
 * @param subTitle The text that appears underneath [title] to provide extra information or hint
 * The close icon and page number will not be shown at the same time. If both are set, close icon
 * will not be shown in favour of page number
 */
@Composable
fun BanklyTitleBar(
    onBackPress: (() -> Unit)? = null,
    title: String,
    subTitle: AnnotatedString = buildAnnotatedString { append("") },
    currentPage: Int = 0,
    totalPage: Int = 0,
    isLoading: Boolean = false,
    onTrailingIconClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    trailingIcon: @Composable (() -> Unit) -> Unit = { onIconClick ->
        BanklyClickableIcon(
            modifier = Modifier.size(36.dp),
            icon = BanklyIcons.Cancel,
            onClick = onIconClick,
            rippleColor = MaterialTheme.colorScheme.error,
        )
    },
) {
    val shouldShowPageNumber by remember(currentPage, totalPage) {
        mutableStateOf(currentPage > 0 && totalPage > 0 && currentPage <= totalPage)
    }

    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxWidth()
            .padding(bottom = if (subTitle.isNotEmpty()) 24.dp else 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onBackPress != null) {
                Box(modifier = Modifier.weight(1f)) {
                    BanklyBackButton(onClick = onBackPress)
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .weight(1f),
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
                maxLines = 1,
            )
            if (shouldShowPageNumber) {
                Text(
                    text = "Step $currentPage/$totalPage",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.tertiary),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .width(90.dp),
                )
            } else if (onTrailingIconClick != null) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    trailingIcon(onTrailingIconClick)
                }
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
                trackColor = MaterialTheme.colorScheme.primaryContainer,
            )
        } else {
            Spacer(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .height(2.dp),
            )
        }
        if (shouldShowPageNumber) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 24.dp),
                progress = (currentPage.toFloat() / totalPage.toFloat()) * 1f,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer,
            )
        }

        if (subTitle.isNotEmpty()) {
            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.tertiary),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyTitleBarPreview1() {
    BanklyTheme {
        BanklyTitleBar(
            onBackPress = {},
            title = "Log In",
            subTitle = buildAnnotatedString { append("Fill in your sign in details to access your account") },
            currentPage = 1,
            totalPage = 2,

        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyTitleBarPreview2() {
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In",
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyTitleBarPreview3() {
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In",
            subTitle = buildAnnotatedString { append("Fill in your sign in details to access your account") },
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyTitleBarPreview4() {
    BanklyTheme {
        BanklyTitleBar(
            title = "Log In",
            subTitle = buildAnnotatedString { append("Fill in your sign in details to access your account") },
            currentPage = 2,
            totalPage = 2,
            isLoading = true,
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyTitleBarPreview5() {
    BanklyTheme {
        BanklyTitleBar(
            onBackPress = { },
            title = "Log In",
            isLoading = true,
            currentPage = 1,
            totalPage = 5,
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyTitleBarPreview6() {
    BanklyTheme {
        BanklyTitleBar(
            onBackPress = { },
            title = "Select Account Type",
            isLoading = true,
            onTrailingIconClick = {},
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyTitleBarPreview7() {
    BanklyTheme {
        BanklyTitleBar(
            onBackPress = { },
            title = "Select Account Type",
            isLoading = true,
            onTrailingIconClick = {},
            trailingIcon = { onClick: () -> Unit ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
                                .toSpanStyle(),
                        ) {
                            append("Export")
                        }
                    },
                )
            },
        )
    }
}
