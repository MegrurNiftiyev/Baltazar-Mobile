package com.example.baltazar.feature.explore.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.navigation.About
import com.example.baltazar.core.core.navigation.Cart
import com.example.baltazar.core.core.navigation.Help
import com.example.baltazar.core.core.navigation.Notifications
import com.example.baltazar.core.core.navigation.Settings
import com.example.baltazar.core.domain.model.User
import compose.icons.TablerIcons
import compose.icons.tablericons.Bell
import compose.icons.tablericons.Help as HelpIcon
import compose.icons.tablericons.InfoCircle
import compose.icons.tablericons.Settings as SettingsIcon
import compose.icons.tablericons.ShoppingCart

@Composable
fun ExploreSidebar(
    user: User,
    isUserLoading: Boolean = false,
    onPageClick: (Any) -> Unit,
    onUserClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier.width(300.dp),
        drawerShape = RectangleShape,
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerContentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = Paddings.Medium, vertical = Paddings.Small)
        ) {
            UserTile(
                user = user,
                isLoading = isUserLoading,
                onClick = onUserClick
            )

            Spacer(modifier = Modifier.height(Spaces.Small))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(Spaces.Small))

            Column {
                PageTile(
                    title = stringResource(R.string.sidebar_cart),
                    icon = TablerIcons.ShoppingCart,
                    onClick = { onPageClick(Cart) }
                )

                PageTile(
                    title = stringResource(R.string.sidebar_notifications),
                    icon = TablerIcons.Bell,
                    onClick = { onPageClick(Notifications) }
                )

                PageTile(
                    title = stringResource(R.string.sidebar_settings),
                    icon = TablerIcons.SettingsIcon,
                    onClick = { onPageClick(Settings) }
                )

                PageTile(
                    title = stringResource(R.string.sidebar_about),
                    icon = TablerIcons.InfoCircle,
                    onClick = { onPageClick(About) }
                )

                PageTile(
                    title = stringResource(R.string.sidebar_help),
                    icon = TablerIcons.HelpIcon,
                    onClick = { onPageClick(Help) }
                )
            }
        }
    }
}
