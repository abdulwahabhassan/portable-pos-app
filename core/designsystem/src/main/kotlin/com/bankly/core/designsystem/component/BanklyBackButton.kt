package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun BanklyBackButton(onClick: () -> Unit) {
    Icon(
        imageVector = BanklyIcons.ArrowLeft,
        contentDescription = "Left Arrow",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier

            .clip(MaterialTheme.shapes.small)
            .clickable(
                onClick = onClick,
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            .padding(4.dp)
            .size(32.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small,
            )
    )
}

/**
 * Preview
 */
@Composable
@Preview(showBackground = true)
private fun BanklyBackButtonPreview() {
    BanklyTheme {
        BanklyBackButton(
            onClick = {}
        )
    }
}