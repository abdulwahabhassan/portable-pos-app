package com.bankly.feature.dashboard.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.model.entity.Feature

@Composable
fun FeatureCard(
    feature: com.bankly.core.model.entity.Feature,
    onClick: () -> Unit,
    isEnable: Boolean = true,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = onClick,
                enabled = isEnable,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.primary,
                ),
            ),
        shape = MaterialTheme.shapes.medium,
        colors = if (isEnable) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            )
        } else {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.tertiary,
            )
        },
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Column {
                Icon(
                    modifier = Modifier.size(32.dp),
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
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun QuickActionCardPreview() {
    BanklyTheme {
        FeatureCard(
            com.bankly.core.model.entity.Feature.PayWithCard(),
            onClick = { },
            isEnable = true,
        )
    }
}
