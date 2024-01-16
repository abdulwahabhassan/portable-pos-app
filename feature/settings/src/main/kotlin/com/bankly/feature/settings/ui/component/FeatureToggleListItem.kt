package com.bankly.feature.settings.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklySwitchButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.model.entity.Feature

@Composable
internal fun FeatureToggleListItem(
    feature: com.bankly.core.model.entity.Feature,
    onToggle: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(0.5.dp),
    ) {
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 0.3.dp,
                    shape = MaterialTheme.shapes.medium,
                    clip = true,
                )
                .clip(MaterialTheme.shapes.medium)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium,
                )
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    painter = painterResource(
                        id = when (feature) {
                            is com.bankly.core.model.entity.Feature.PayWithCard -> BanklyIcons.PayWithCard
                            is com.bankly.core.model.entity.Feature.PayWithTransfer -> BanklyIcons.PayWithTransfer
                            is com.bankly.core.model.entity.Feature.CardTransfer -> BanklyIcons.CardTransfer
                            is com.bankly.core.model.entity.Feature.SendMoney -> BanklyIcons.SendMoney
                            is com.bankly.core.model.entity.Feature.PayBills -> BanklyIcons.PayBills
                            is com.bankly.core.model.entity.Feature.CheckBalance -> BanklyIcons.CheckBalance
                            is com.bankly.core.model.entity.Feature.PayWithUssd -> BanklyIcons.PayWithUssd
                            is com.bankly.core.model.entity.Feature.Float -> BanklyIcons.Float
                            is com.bankly.core.model.entity.Feature.EndOfDay -> BanklyIcons.EndOfDay
                            is com.bankly.core.model.entity.Feature.NetworkChecker -> BanklyIcons.NetworkChecker
                            is com.bankly.core.model.entity.Feature.Settings -> BanklyIcons.Settings
                        },
                    ),
                    contentDescription = null,
                    alignment = Alignment.Center,
                )
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(
                modifier = Modifier.padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                BanklySwitchButton(
                    checked = feature.isEnabled,
                    isEnabled = true,
                    onCheckedChange = onToggle,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SavedBeneficiaryItemPreview() {
    BanklyTheme {
        FeatureToggleListItem(
            feature = com.bankly.core.model.entity.Feature.SendMoney(),
            onToggle = {},
        )
    }
}
