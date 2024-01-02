package com.bankly.feature.faq.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.common.ui.view.ComingSoonView
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.faq.R

@Composable
internal fun FaqRoute(
    onBackPress: () -> Unit,
) {
    FaqScreen(
        onBackPress = onBackPress,
    )
}

@Composable
private fun FaqScreen(
    onBackPress: () -> Unit,
) {
    BackHandler {
        onBackPress()
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_faq),
            )
        },
    ) { paddingValues: PaddingValues ->
        ComingSoonView(
            modifier = Modifier.padding(paddingValues),
            onOkayButtonClick = onBackPress
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun ContactUsScreenPreview() {
    BanklyTheme {
        FaqScreen(
            onBackPress = {},
        )
    }
}
