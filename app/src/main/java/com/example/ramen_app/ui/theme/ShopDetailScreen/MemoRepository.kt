package com.example.ramen_app.ui.theme.ShopDetailScreen

import android.content.Context

class MemoRepository(context: Context) {
    private val prefs = context.getSharedPreferences("shop_memo", Context.MODE_PRIVATE)
    fun getMemo(shopId: String): String {
        return prefs.getString("memo_$shopId", "") ?: ""
    }
    fun saveMemo(shopId: String, memo: String) {
        prefs.edit().putString("memo_$shopId", memo).apply()
    }
    fun getRating(shopId: String): Int {
        return prefs.getInt("rating_$shopId", 0)
    }
    fun saveRating(shopId: String, rating: Int) {
        prefs.edit().putInt("rating_$shopId", rating).apply()
    }
}