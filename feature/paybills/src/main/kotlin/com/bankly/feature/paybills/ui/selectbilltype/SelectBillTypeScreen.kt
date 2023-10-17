package com.bankly.feature.paybills.ui.selectbilltype

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.paybills.component.BillTypeCard
import com.bankly.feature.paybills.model.BillType

@Composable
internal fun SelectBillTypeRoute(
    onBillTypeSelected: (BillType) -> Unit,
    onBackPress: () -> Unit,
) {
    BackHandler { onBackPress() }
    SelectBillTypeScreen(
        onBackPress = onBackPress,
        onBillTypeSelected = onBillTypeSelected,
    )
}

@Composable
internal fun SelectBillTypeScreen(
    onBackPress: () -> Unit,
    onBillTypeSelected: (BillType) -> Unit,
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = "Pay Bills",
                subTitle = buildAnnotatedString {
                    append("Please select bill type")
                },
            )
        },
    ) { padding ->
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(padding),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(BillType.values()) { billType: BillType ->
                BillTypeCard(
                    billType = billType,
                    onClick = {
                        when (billType) {
                            BillType.AIRTIME,
                            BillType.CABLE_TV,
                            BillType.INTERNET_DATA,
                            BillType.ELECTRICITY,
                            -> onBillTypeSelected(billType)
                        }
                    },
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun  PayBillsScreenPreview() {
    BanklyTheme {
        SelectBillTypeScreen(
            onBackPress = {},
            onBillTypeSelected = {}
        )
    }
}
