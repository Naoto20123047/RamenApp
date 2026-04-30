package com.example.ramen_app.ui.theme.ShopListScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramen_app.ui.theme.RamenAPI.Shop
import com.example.ramen_app.ui.theme.RamenAPI.ramenApiService
import kotlinx.coroutines.launch

class ShopListViewModel : ViewModel() {
    var shops by mutableStateOf<List<Shop>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchShops()
    }

    fun fetchShops() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                shops = ramenApiService.getShops().shops
            } catch (e: Exception) {
                Log.e("ShopListViewModel", "Error fetching shops", e)
                errorMessage = "ショップ一覧の取得に失敗しました"
            } finally {
                isLoading = false
            }
        }
    }
}
