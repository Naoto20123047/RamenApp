package com.example.ramen_app.ui.theme.NewsScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val NEWS_ROUTE = "news"

fun NavGraphBuilder.newsScreen() {
    composable(NEWS_ROUTE) {
        NewsScreen()
    }
}

fun NavController.navigateToNews() {
    this.navigate(NEWS_ROUTE) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}