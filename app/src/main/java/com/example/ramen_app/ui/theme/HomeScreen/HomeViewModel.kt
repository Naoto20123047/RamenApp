package com.example.ramen_app.ui.theme.HomeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramen_app.ui.theme.RamenAPI.Shop
import com.example.ramen_app.ui.theme.RamenAPI.ramenApiService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // ピックアップ表示用に先頭3件だけ取得
    var featuredShops by mutableStateOf<List<Shop>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchFeatured()
    }

    fun fetchFeatured() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                // RamenApi.kt の ramenApiService を使用
                featuredShops = ramenApiService.getShops(page = 1, perPage = 3).shops
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching featured shops", e)
                errorMessage = "読み込みに失敗しました"
            } finally {
                isLoading = false
            }
        }
    }
}
