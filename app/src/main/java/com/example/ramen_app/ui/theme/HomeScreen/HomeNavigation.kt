package com.example.ramen_app.ui.theme.HomeScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen(
    onNavigateToList: () -> Unit,
    onShopClick: (String) -> Unit
) {
    composable(HOME_ROUTE) {
        HomeScreen(
            onNavigateToList = onNavigateToList,
            onShopClick = onShopClick
        )
    }
}

fun NavController.navigateToHome() {
    this.navigate(HOME_ROUTE)
}
