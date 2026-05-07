package com.example.ramen_app.ui.theme.ShopDetailScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramen_app.ui.theme.RamenAPI.Shop
import com.example.ramen_app.ui.theme.RamenAPI.ramenApiService
import kotlinx.coroutines.launch

class ShopDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val shopId: String? = savedStateHandle["shopId"]
    var shop by mutableStateOf<Shop?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    init {
        shopId?.let { fetchShop(it) }
    }
    fun fetchShop(id: String = shopId ?: "") {
        if (id.isEmpty()) return
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                shop = ramenApiService.getShop(id).shop
            } catch (e: Exception) {
                Log.e("ShopDetailViewModel", "Error fetching shop detail", e)
                errorMessage = "店舗情報の取得に失敗しました"
            } finally {
                isLoading = false
            }
        }
    }
}
