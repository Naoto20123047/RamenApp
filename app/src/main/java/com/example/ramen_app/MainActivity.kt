package com.example.ramen_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ramen_app.ui.theme.HomeScreen.HOME_ROUTE
import com.example.ramen_app.ui.theme.HomeScreen.homeScreen
import com.example.ramen_app.ui.theme.HomeScreen.navigateToHome
import com.example.ramen_app.ui.theme.NewsScreen.NEWS_ROUTE
import com.example.ramen_app.ui.theme.NewsScreen.navigateToNews
import com.example.ramen_app.ui.theme.NewsScreen.newsScreen
import com.example.ramen_app.ui.theme.Ramen_AppTheme
import com.example.ramen_app.ui.theme.ShopDetailScreen.navigateToShopDetail
import com.example.ramen_app.ui.theme.ShopDetailScreen.shopDetailScreen
import com.example.ramen_app.ui.theme.ShopListScreen.SHOP_LIST_ROUTE
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

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object ShopList : BottomNavItem(SHOP_LIST_ROUTE, "店舗一覧", Icons.Filled.List)
    object Home : BottomNavItem(HOME_ROUTE, "ホーム", Icons.Filled.Home)
    object News : BottomNavItem(NEWS_ROUTE, "News", Icons.Filled.Newspaper)
}

val bottomNavItems = listOf(
    BottomNavItem.ShopList,
    BottomNavItem.Home,
    BottomNavItem.News
)

val bottomNavRoutes = setOf(HOME_ROUTE, SHOP_LIST_ROUTE, NEWS_ROUTE)

@Composable
fun RamenAppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                when (item) {
                                    is BottomNavItem.Home -> navController.navigateToHome()
                                    is BottomNavItem.ShopList -> navController.navigateToShopList()
                                    is BottomNavItem.News -> navController.navigateToNews()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
            modifier = Modifier.padding(padding)
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
            newsScreen()
        }
    }
}