package com.bankly.feature.networkchecker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bankly.core.designsystem.theme.BanklyErrorColor
import com.bankly.core.designsystem.theme.BanklySuccessColor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.BanklyWarningColor
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.BankNetwork
import kotlin.math.roundToInt

@Composable
internal fun BankNetworkListItem(
    bankNetwork: BankNetwork,
) {
    val statusColor = when (bankNetwork.networkPercentage) {
        in 1.00..50.00 -> BanklyErrorColor.errorColor
        in 51.00..69.00 -> BanklyWarningColor.warningColor
        else -> if (bankNetwork.totalCount > 0L && bankNetwork.networkPercentage == 0.00)
            BanklyErrorColor.errorColor else BanklySuccessColor.successColor
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .shadow(
                elevation = 0.3.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true,
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp),
            )
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                model = bankNetwork.bankIcon,
                contentDescription = null,
                alignment = Alignment.Center,
            )
            Text(
                text = bankNetwork.bankName,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier
                .size(width = 64.dp, height = 40.dp)
                .background(color = statusColor, shape = MaterialTheme.shapes.small)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bankNetwork.networkPercentage.roundToInt().toString().plus("%"),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SavedBeneficiaryItemPreview() {
    BanklyTheme {
        BankNetworkListItem(
            bankNetwork = BankNetwork(
                bankName = "Hassan Abdulwahab",
                bankIcon = "",
                networkPercentage = 100.00,
                totalCount = 100,
            ),
        )
    }
}
