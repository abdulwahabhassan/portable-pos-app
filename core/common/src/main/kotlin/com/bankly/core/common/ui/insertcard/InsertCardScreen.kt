package com.bankly.core.common.ui.insertcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun InsertCardRoute(
    onCardInserted: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    InsertCardScreen(
        onCardInserted = onCardInserted,
        onBackPress = onBackPress,
        onCloseClick = onCloseClick
    )
}

@Composable
fun InsertCardScreen(
    onCardInserted: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_insert_card),
                onCloseClick = onCloseClick
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = 150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    modifier = Modifier.clickable {
                        onCardInserted()
                    },
                    painter = painterResource(id = com.bankly.core.designsystem.R.drawable.ic_insert_card_into_pos),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(R.string.msg_insert_your_card_into_the_machine),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun InsertCardScreenPreview() {
    BanklyTheme {
        InsertCardScreen(
            onCardInserted = {},
            onBackPress = {},
            onCloseClick = {}
        )
    }
}