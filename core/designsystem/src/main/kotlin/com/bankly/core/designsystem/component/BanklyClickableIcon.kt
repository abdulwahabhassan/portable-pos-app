package com.bankly.core.designsystem.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.R
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun BanklyClickableIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit,
    color: Color = Color.Unspecified,
    rippleColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = stringResource(R.string.desc_clickable_icon),
        tint = color,
        modifier = modifier
            .clip(shape)
            .clickable(
                onClick = onClick,
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = rippleColor,
                ),
            ),

    )
}

@Composable
fun BanklyClickableIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color = Color.Unspecified,
    rippleColor: Color = MaterialTheme.colorScheme.primary,
) {
    Icon(
        imageVector = icon,
        contentDescription = stringResource(R.string.desc_clickable_icon),
        tint = color,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                onClick = onClick,
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = rippleColor,
                ),
            ),
    )
}

@Composable
@Preview(showBackground = true)
private fun BanklyClickableIconPreview1() {
    BanklyTheme {
        BanklyClickableIcon(
            modifier = Modifier.size(32.dp).padding(4.dp),
            icon = BanklyIcons.Notification_Bell_01,
            onClick = {},
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyClickableIconPreview2() {
    BanklyTheme {
        BanklyClickableIcon(
            modifier = Modifier.size(32.dp).padding(4.dp),
            icon = BanklyIcons.ArrowLeft,
            onClick = {},
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
