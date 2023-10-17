package com.bankly.core.common.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklySearchBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> SearchableSelectionListView(
    isListLoading: Boolean,
    listItems: List<T>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    itemToString: (T) -> String,
    drawItem: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit = { item, selected, itemEnabled, onClick ->
        SelectableListItem(
            text = itemToString(item),
            selected = selected,
            enabled = itemEnabled,
            onClick = onClick,
            startIcon = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = BanklyIcons.Mtn),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
        )
    },
    searchPredicate: ((T) -> Boolean)? = null,
    title: String,
    showCloseIcon: Boolean = true,
    onCloseIconClick: () -> Unit,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
) {

    val items = if (searchPredicate != null)
        remember(listItems, searchQuery) {
            mutableStateOf(listItems.filter(searchPredicate))
        }.value else listItems

    if (isListLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(50.dp)
                    .size(50.dp),
                strokeWidth = 8.dp,
                trackColor = MaterialTheme.colorScheme.primaryContainer,
                strokeCap = StrokeCap.Round,
            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            if (showCloseIcon) {
                BanklyClickableIcon(
                    icon = BanklyIcons.Close,
                    onClick = onCloseIconClick,
                    shape = CircleShape
                )
            }
        }
        if (listItems.isEmpty()) {
            EmptyStateView()
        } else {
            val listState = rememberLazyListState()
            if (selectedIndex > -1) {
                LaunchedEffect("ScrollToSelected") {
                    listState.scrollToItem(index = selectedIndex)
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
            ) {
                stickyHeader {
                    if (searchPredicate != null) {
                       Surface(
                       color = MaterialTheme.colorScheme.surfaceVariant
                       ) {
                           BanklySearchBar(
                               modifier = Modifier,
                               query = searchQuery,
                               onQueryChange = onSearchQueryChange,
                               searchPlaceholder = stringResource(R.string.msg_search_by_keyword),
                           )
                       }
                    }
                }

                itemsIndexed(items) { index, item ->
                    val selectedItem = index == selectedIndex
                    drawItem(
                        item,
                        selectedItem,
                        true,
                    ) {
                        onItemSelected(index, item)
                    }
                    if (index < items.lastIndex) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SelectableListItem(
    text: String,
    selected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
    startIcon: @Composable (() -> Unit)? = null,
) {
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.inversePrimary
        selected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.tertiary
    }
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = Modifier
                .clickable(enabled) { onClick() }
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (startIcon != null) {
                    startIcon()
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = PreviewColor.white)
@Composable
private fun SearchableSelectionListPreview() {
    BanklyTheme {
        SearchableSelectionListView(
            isListLoading = false,
            listItems = emptyList(),
            itemToString = { item: String ->
                item.substringBefore(" ")
            },
            onItemSelected = { _, _ -> },

            title = "Select Network Provider",
            onCloseIconClick = { },
        )
    }
}
