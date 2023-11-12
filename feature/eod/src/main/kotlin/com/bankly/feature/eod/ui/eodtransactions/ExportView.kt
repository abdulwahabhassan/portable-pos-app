package com.bankly.feature.eod.ui.eodtransactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.eod.ui.component.ExportEodItem
import com.bankly.feature.eod.model.EodExport

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExportEodView(onCloseClick: () -> Unit) {

    val items = EodExport.values().toList()
    var selectedExportType: EodExport? by remember {
        mutableStateOf(null)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        flingBehavior = ScrollableDefaults.flingBehavior(),
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = com.bankly.feature.eod.R.string.title_export_eod_as),
                    style = MaterialTheme.typography.titleMedium
                )
                BanklyClickableIcon(
                    icon = BanklyIcons.Close,
                    onClick = onCloseClick,
                    shape = CircleShape
                )
            }
        }

        itemsIndexed(items) { index, item ->
            ExportEodItem(
                selected = item == selectedExportType,
                text = item.title,
                onClick = {
                    selectedExportType = item
                },
                trailingIcon = {
                    if (item == selectedExportType) Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = BanklyIcons.BoxCheck),
                        contentDescription = null,
                        tint = Color.Unspecified
                    ) else Box(modifier = Modifier.size(24.dp))
                }
            )
            if (index < items.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }

        item {
            BanklyFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                text = stringResource(com.bankly.feature.eod.R.string.action_export_eod),
                onClick = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
fun ExportEodPreview() {
    BanklyTheme {
        ExportEodView(onCloseClick = {})
    }
}
