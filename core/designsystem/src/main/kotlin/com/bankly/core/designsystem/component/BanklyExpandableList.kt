package com.bankly.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T>BanklyExpandableList(
    title: String,
    items: List<T>,
    isExpanded: Boolean,
    onClickExpand: (isExpanded: Boolean) -> Unit,
    onItemSelected: (item: T) -> Unit,
    transformItemToString: (T) -> String = { it.toString() },
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.small)
                        .clickable(
                            onClick = {
                                onClickExpand(isExpanded)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = true,
                                color = MaterialTheme.colorScheme.primary
                            )
                        ),
                    painter = painterResource(if (isExpanded) BanklyIcons.Chevron_Up else BanklyIcons.Chevron_Down),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        items(items) { item ->
            AnimatedVisibility(visible = isExpanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onItemSelected(item)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = true,
                                color = MaterialTheme.colorScheme.primary
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    BankListItem(bankName = transformItemToString(item))
                }
            }
        }
    }
}

@Composable
internal fun BankListItem(
    bankName: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = bankName,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
internal fun BanklyExpandableListPreview() {
    BanklyTheme {
        BanklyExpandableList(
            "Commercial banks",
            items = listOf("Kuda MFB", "Carbon MFB", "GTB Bank", "First Bank of Nigeria (FBN)"),
            isExpanded = true,
            onClickExpand = {},
            onItemSelected = {},

        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
internal fun BankListItemPreview() {
    BanklyTheme {
        BankListItem("Bankly MFB")
    }
}