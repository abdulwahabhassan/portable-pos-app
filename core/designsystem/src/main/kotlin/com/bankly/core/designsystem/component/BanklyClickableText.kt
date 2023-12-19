package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@Composable
fun BanklyClickableText(
    text: AnnotatedString,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    indicationColor: Color = MaterialTheme.colorScheme.primary,
    backgroundShape: Shape = CircleShape,
    trailingIcon: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
) {
    Row(
        modifier = Modifier
            .background(
                shape = backgroundShape,
                color = backgroundColor,
            )
            .clip(backgroundShape)
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = indicationColor,
                ),
            )
            .padding(vertical = 6.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = textStyle,
            textAlign = TextAlign.Center,
        )
        if (trailingIcon != null) {
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            trailingIcon()
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyClickableTextPreview1() {
    BanklyTheme {
        BanklyClickableText(
            text = buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.bodyMedium.toSpanStyle(),
                ) {
                    append("Had coffee yet? ")
                }
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
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
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
