package com.example.ramen_app.ui.theme.ShopDetailScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val SHOP_DETAIL_ROUTE = "detail"
const val SHOP_ID_ARG = "shopId"

fun NavGraphBuilder.shopDetailScreen(
    onBack: () -> Unit
) {
    composable(
        route = "$SHOP_DETAIL_ROUTE/{$SHOP_ID_ARG}",
        arguments = listOf(
            navArgument(SHOP_ID_ARG) { type = NavType.StringType }
        )
    ) {
        ShopDetailScreen(onBack = onBack)
    }
}

fun NavController.navigateToShopDetail(shopId: String) {
    this.navigate("$SHOP_DETAIL_ROUTE/$shopId")
}
