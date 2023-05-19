package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun <T : Enum<T>> BanklyTabBar(
    tabs: List<T> = emptyList(),
    onTabClick: (T) -> Unit,
    selectedTab: T,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.forEach { category ->
            Box(
                modifier = Modifier
                    .height(42.dp)
                    .weight(1f)
                    .padding(4.dp)
                    .background(
                        color = if (selectedTab == category) {
                            MaterialTheme.colorScheme.primary
                        } else Color.Unspecified,
                        shape = MaterialTheme.shapes.small,
                    )
                    .clip(MaterialTheme.shapes.small)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = true,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            onTabClick(category)
                        }
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (selectedTab == category) {
                            MaterialTheme.colorScheme.onPrimary
                        } else
                            MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}

enum class Sample {
    Bankly, Bloom
}

@Composable
@Preview(showBackground = true)
fun BanklyTabBarPreview() {

    BanklyTheme {
        BanklyTabBar(
            tabs = Sample.values().toList(),
            onTabClick = {},
            selectedTab = Sample.Bankly
        )

    }

}
