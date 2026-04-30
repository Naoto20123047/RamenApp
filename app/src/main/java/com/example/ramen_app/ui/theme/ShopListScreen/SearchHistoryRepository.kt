package com.example.ramen_app.ui.theme.ShopListScreen

import android.content.Context

class SearchHistoryRepository(context: Context) {
    private val prefs = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val KEY = "history"
    private val MAX = 3
    private val DELIMITER = "||"

    fun getHistory(): List<String> {
        val raw = prefs.getString(KEY, "") ?: ""
        if (raw.isBlank()) return emptyList()
        return raw.split(DELIMITER).filter { it.isNotBlank() }.take(MAX)
    }

    fun addHistory(query: String) {
        if (query.isBlank()) return
        val current = getHistory().toMutableList()
        current.remove(query)
        current.add(0, query)
        prefs.edit()
            .putString(KEY, current.take(MAX).joinToString(DELIMITER))
            .apply()
    }

    fun clearHistory() {
        prefs.edit().remove(KEY).apply()
    }
}
