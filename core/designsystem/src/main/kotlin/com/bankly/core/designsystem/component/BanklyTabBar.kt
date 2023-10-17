package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme
import java.util.Locale

@Composable
fun <T : Enum<T>> BanklyTabBar(
    modifier: Modifier = Modifier,
    tabs: List<T>,
    onTabClick: (T) -> Unit,
    selectedTab: T,
    selectedTabColor: Color = MaterialTheme.colorScheme.primary,
    selectedTabTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedTabTextColor: Color = MaterialTheme.colorScheme.primary,
    rippleColor: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        tabs.forEach { category ->
            Box(
                modifier = Modifier
                    .height(46.dp)
                    .weight(1f)
                    .padding(4.dp)
                    .background(
                        color = if (selectedTab == category) {
                            selectedTabColor
                        } else {
                            Color.Unspecified
                        },
                        shape = MaterialTheme.shapes.small,
                    )
                    .clip(MaterialTheme.shapes.small)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = true,
                            color = rippleColor,
                        ),
                        onClick = {
                            onTabClick(category)
                        },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = category.name.lowercase(Locale.ROOT)
                        .split("_").joinToString(" ") { word ->
                            word.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                            }
                        },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (selectedTab == category) {
                            selectedTabTextColor
                        } else {
                            unselectedTabTextColor
                        },
                        fontWeight = if (selectedTab == category) {
                            FontWeight.Medium
                        } else {
                            FontWeight.Normal
                        }
                    ),
                )
            }
        }
    }
}

enum class Sample {
    BANKLY, BLOOM
}

@Composable
@Preview(showBackground = true)
private fun BanklyTabBarPreview() {
    BanklyTheme {
        BanklyTabBar(
            tabs = Sample.values().toList(),
            onTabClick = {},
            selectedTab = Sample.BANKLY,
        )
    }
}
