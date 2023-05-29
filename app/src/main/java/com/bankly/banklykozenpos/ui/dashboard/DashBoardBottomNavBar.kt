package com.bankly.banklykozenpos.ui.dashboard

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.bankly.banklykozenpos.R
import com.bankly.banklykozenpos.navigation.BottomNavDestination

@Composable
fun DashBoardBottomNavBar(
    destinations: List<BottomNavDestination>,
    onNavigateToBottomNavDestination: (BottomNavDestination) -> Unit,
    currentBottomNavDestination: BottomNavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 0.dp
    ) {
        destinations.forEach { destination ->
            val selected = currentBottomNavDestination == destination
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToBottomNavDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    Icon(
                        painter = painterResource(id = icon!!),
                        contentDescription = stringResource(R.string.desc_navigation_icon),
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                },
                label = { Text(destination.title ?: "", style = MaterialTheme.typography.labelSmall.copy()) },

                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    selectedIconColor = Color.Unspecified,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Unspecified,
                    unselectedTextColor = MaterialTheme.colorScheme.inversePrimary
                )
            )
        }
    }

}


@Composable
@Preview(showBackground = true)
fun BottomNavBarPreview() {
    DashBoardBottomNavBar(
        destinations = BottomNavDestination.values().toList(),
        onNavigateToBottomNavDestination = {},
        currentBottomNavDestination = BottomNavDestination.HOME
    )

}