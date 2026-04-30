package com.example.ramen_app.ui.theme.ShopListScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SHOP_LIST_ROUTE = "list"

fun NavGraphBuilder.shopListScreen(
    onShopClick: (String) -> Unit,
    onBack: () -> Unit
) {
    composable(SHOP_LIST_ROUTE) {
        ShopListScreen(
            onShopClick = onShopClick,
            onBack = onBack
        )
    }
}

fun NavController.navigateToShopList() {
    this.navigate(SHOP_LIST_ROUTE)
}
