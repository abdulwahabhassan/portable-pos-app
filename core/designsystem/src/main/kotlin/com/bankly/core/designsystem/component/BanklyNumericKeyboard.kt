package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme


@Composable
@Preview(showBackground = true)
fun BanklyNumericKeyboardPreview() {
    BanklyTheme {
        BanklyNumericKeyboard(
            onKeyPressed = {}
        )
    }
}

@Composable
fun BanklyNumericKeyboard(
    onKeyPressed: (PassCodeKey) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(PassCodeKey.values().size / 4),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(PassCodeKey.values()) { key ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .width(80.dp)
                    .height(40.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(
                        color = if (key == PassCodeKey.DONE) MaterialTheme.colorScheme.primary
                        else if (key == PassCodeKey.DELETE) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable(
                        onClick = {
                            onKeyPressed(key)
                        },
                        enabled = true,
                        role = Role.Button,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = true,
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = key.value,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }
        }
    }
}
