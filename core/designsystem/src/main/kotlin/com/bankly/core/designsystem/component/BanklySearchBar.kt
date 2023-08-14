package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BanklySearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    searchPlaceholder: String,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 2.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.medium,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            bottomStart = 8.dp,
                        ),
                    ),

                ) {
                Icon(
                    modifier = modifier
                        .align(Alignment.Center)
                        .size(20.dp)
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                bottomStart = 8.dp,
                            ),
                        ),
                    painter = painterResource(id = BanklyIcons.Search),
                    contentDescription = "Search icon",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            TextField(
                modifier = modifier,
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                placeholder = {
                    Text(
                        text = searchPlaceholder,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchBarPreview() {
    BanklyTheme {
        BanklySearchBar(
            modifier = Modifier,
            query = "",
            onQueryChange = {},
            searchPlaceholder = "Type a keyword ...",
        )
    }
}