package com.bankly.feature.contactus.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.contactus.component.ContactUsListItem
import com.bankly.feature.contactus.model.ContactUsOption

@Composable
internal fun ContactUsRoute(
    onBackPress: () -> Unit,
) {
    ContactUsScreen(
        onBackPress = onBackPress,
    )
}

@Composable

private fun ContactUsScreen(
    onBackPress: () -> Unit,
) {
    BackHandler {
        onBackPress()
    }


    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = "Contact Us",
                subTitle = buildAnnotatedString {
                    withStyle(
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary)
                            .toSpanStyle()
                    ) {
                        append("Need help? Contact us via our support channels")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(ContactUsOption.values()) { option: ContactUsOption ->
                ContactUsListItem(
                    option = option,
                    onClick = {

                    }
                )
            }
        }
    }

}


@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun ContactUsScreenPreview() {
    BanklyTheme {
        ContactUsScreen(
            onBackPress = {},
        )
    }
}