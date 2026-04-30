package com.example.ramen_app.ui.theme.ShopListScreen

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoriteRepository(context: Context) {
    private val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val _favoriteIds = MutableStateFlow(getFavoritesFromPrefs())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds

    private fun getFavoritesFromPrefs(): Set<String> {
        return prefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()
    }

    fun toggleFavorite(shopId: String) {
        val current = _favoriteIds.value.toMutableSet()
        if (current.contains(shopId)) {
            current.remove(shopId)
        } else {
            current.add(shopId)
        }
        prefs.edit().putStringSet("favorite_ids", current).apply()
        _favoriteIds.value = current
    }
}
