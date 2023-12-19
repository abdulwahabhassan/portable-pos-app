package com.bankly.feature.contactus.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.contactus.model.ContactUsOption

@Composable
internal fun ContactUsListItem(
    option: ContactUsOption,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium,
                )
                .clip(MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = option.icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = option.title,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Text(
                text = option.address,
                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary),
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SupportListItemPreview() {
    BanklyTheme {
        ContactUsListItem(option = ContactUsOption.Email)
    }
}
