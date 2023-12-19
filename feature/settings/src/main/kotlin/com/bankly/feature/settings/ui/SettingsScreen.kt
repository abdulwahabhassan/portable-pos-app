package com.bankly.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.entity.Feature
import com.bankly.feature.settings.R
import com.bankly.feature.settings.ui.component.FeatureToggleListItem

@Composable
internal fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(
        onBackPress = onBackPress,
        screenState = screenState,
    ) { uiEvent: SettingsScreenEvent ->
        viewModel.sendEvent(uiEvent)
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendEvent(SettingsScreenEvent.LoadUiData)
    })
}

@Composable
private fun SettingsScreen(
    onBackPress: () -> Unit,
    screenState: SettingsScreenState,
    onUiEvent: (SettingsScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            Column {
                BanklyTitleBar(
                    onBackPress = onBackPress,
                    title = stringResource(R.string.title_settings),
                )
                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 2.dp)) {
                }
            }
        },
    ) { paddingValues: PaddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            items(screenState.features, Feature::title) { feature: Feature ->
                FeatureToggleListItem(
                    feature = feature,
                    onToggle = {
                        onUiEvent(
                            SettingsScreenEvent.OnFeatureToggle(
                                toggledFeature = feature,
                                screenState.features,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun SettingsScreenPreview() {
    BanklyTheme {
        SettingsScreen(
            onBackPress = {},
            screenState = SettingsScreenState(
                features = listOf(
                    Feature.SendMoney(),
                    Feature.CardTransfer(),
                ),
            ),
            onUiEvent = {},
        )
    }
}
