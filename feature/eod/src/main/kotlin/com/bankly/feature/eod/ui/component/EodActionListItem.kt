package com.bankly.feature.eod.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.eod.model.EodAction

@Composable
internal fun EodActionListItem(
    eodAction: EodAction,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
                .clickable(
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = true,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = eodAction.icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = eodAction.title,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Icon(
                painter = painterResource(id = BanklyIcons.FilledArrowForward),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun EodActionListItemPreview() {
    BanklyTheme {
        EodActionListItem(eodAction = EodAction.SYNC_EOD) {

        }
    }
}