package com.example.ramen_app.ui.theme.HomeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramen_app.ui.theme.RamenAPI.Shop
import com.example.ramen_app.ui.theme.RamenAPI.ramenApiService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
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
                val result = ramenApiService.getShops(page = 1, perPage = 5).shops
                featuredShops = result
            } catch (e: Exception) {
                errorMessage = "読み込みに失敗しました"
            } finally {
                isLoading = false
            }
        }
    }
}
