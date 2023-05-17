package com.bankly.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.R
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun BanklyClickableIcon(icon: Int, onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = stringResource(R.string.desc_clickable_icon),
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(8.dp)
            .size(32.dp)
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
    )
}

@Composable
fun BanklyClickableIcon(icon: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = icon,
        contentDescription = stringResource(R.string.desc_clickable_icon),
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(8.dp)
            .size(32.dp)
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
    )
}

/**
 * Preview
 */
@Composable
@Preview(showBackground = true)
private fun BanklyClickableIconPreview1() {
    BanklyTheme {
        BanklyClickableIcon(
            icon = BanklyIcons.Notification_Bell_01,
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyClickableIconPreview2() {
    BanklyTheme {
        BanklyClickableIcon(
            icon = BanklyIcons.ArrowLeft,
            onClick = {}
        )
    }
}