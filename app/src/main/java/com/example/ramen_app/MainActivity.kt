package com.example.ramen_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ramen_app.ui.theme.HomeScreen.HOME_ROUTE
import com.example.ramen_app.ui.theme.HomeScreen.homeScreen
import com.example.ramen_app.ui.theme.Ramen_AppTheme
import com.example.ramen_app.ui.theme.ShopDetailScreen.navigateToShopDetail
import com.example.ramen_app.ui.theme.ShopDetailScreen.shopDetailScreen
import com.example.ramen_app.ui.theme.ShopListScreen.favoriteScreen
import com.example.ramen_app.ui.theme.ShopListScreen.navigateToFavorites
import com.example.ramen_app.ui.theme.ShopListScreen.navigateToShopList
import com.example.ramen_app.ui.theme.ShopListScreen.shopListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ramen_AppTheme {
                RamenAppNavigation()
            }
        }
    }
}

@Composable
fun RamenAppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE
    ) {
        homeScreen(
            onNavigateToList = { navController.navigateToShopList() },
            onShopClick = { shopId -> navController.navigateToShopDetail(shopId) }
        )
        shopListScreen(
            onShopClick = { shopId -> navController.navigateToShopDetail(shopId) },
            onBack = { navController.popBackStack() },
            onNavigateToFavorites = { navController.navigateToFavorites() }
        )
        shopDetailScreen(
            onBack = { navController.popBackStack() }
        )
        favoriteScreen(
            onShopClick = { navController.navigateToShopDetail(it) },
            onBack = { navController.popBackStack() }
        )
    }
}
