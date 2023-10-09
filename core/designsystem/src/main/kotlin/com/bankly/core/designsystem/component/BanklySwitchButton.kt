package com.bankly.core.designsystem.component

import androidx.compose.material3.MaterialTheme

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun BanklySwitchButton(
    checked: Boolean,
    isEnabled: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    switchPadding: Dp = 2.dp,
    width: Dp = 50.dp,
    height: Dp = 30.dp,
) {
    val switchSize by remember {
        mutableStateOf(height - switchPadding * 2)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var padding by remember { mutableStateOf(0.dp) }

    padding = if (checked) width - switchSize - switchPadding * 2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = if (checked) padding else 0.dp,
        tween(
            durationMillis = 700,
            delayMillis = 0,
            easing = LinearOutSlowInEasing,
        ),
        label = "",
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(CircleShape)
            .background(if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiaryContainer)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = isEnabled
            ) {
                onCheckedChange(checked)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(switchPadding),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(animateSize)
                    .background(Color.Transparent),
            )

            Box(
                modifier = Modifier
                    .size(switchSize)
                    .clip(CircleShape)
                    .background(Color.White),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BanklySwitchButtonPreview() {
    BanklySwitchButton(
        checked = true,
        onCheckedChange = {},
        isEnabled = true
    )
}