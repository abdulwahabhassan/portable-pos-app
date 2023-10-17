package com.bankly.feature.dashboard.ui.more

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.dashboard.model.Feature
import com.bankly.feature.dashboard.ui.component.FeatureCard

@Composable
fun MoreRoute(
    onFeatureCardClick: (Feature) -> Unit,
    onBackPress: () -> Unit,
) {
    MoreScreen(onFeatureCardClick = onFeatureCardClick, onBackPress = onBackPress)
}

@Composable
fun MoreScreen(onFeatureCardClick: (Feature) -> Unit, onBackPress: () -> Unit) {
    BackHandler {
        onBackPress()
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(Feature.values().filter { it.isQuickAction.not() }) { feature: Feature ->
            FeatureCard(
                feature = feature,
                onClick = {
                    onFeatureCardClick(feature)
                },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun MoreScreenPreview() {
    BanklyTheme {
        MoreScreen(
            onBackPress = {},
            onFeatureCardClick = {}
        )
    }
}
