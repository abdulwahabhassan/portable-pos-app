package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun BanklyClickableText(
    text: AnnotatedString,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(
                shape = CircleShape,
                color = backgroundColor,
            )
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
            .padding(vertical = 4.dp, horizontal = 12.dp),
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.tertiary),
    )
}

@Composable
@Preview(showBackground = true)
private fun BanklyClickableTextPreview1() {
    BanklyTheme {
        BanklyClickableText(
            text = buildAnnotatedString {
                append("Had coffee yet? ")
                withStyle(
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                    ).toSpanStyle(),
                ) {
                    append("Order a cup!")
                }
            },
            onClick = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyClickableTextPreview2() {
    BanklyTheme {
        BanklyClickableText(
            text = buildAnnotatedString {
                append("Click Me")
            },
            onClick = {},
        )
    }
}
