package com.example.ramen_app.ui.theme.ShopListScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val FAVORITE_ROUTE = "favorites"

fun NavGraphBuilder.favoriteScreen(
    onShopClick: (String) -> Unit,
    onBack: () -> Unit
) {
    composable(FAVORITE_ROUTE) {
        FavoriteScreen(
            onShopClick = onShopClick,
            onBack = onBack
        )
    }
}

fun NavController.navigateToFavorites() {
    this.navigate(FAVORITE_ROUTE)
}