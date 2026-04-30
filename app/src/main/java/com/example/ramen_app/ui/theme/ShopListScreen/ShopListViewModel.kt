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
    var searchQuery by mutableStateOf("")
        private set
    var searchHistory by mutableStateOf<List<String>>(emptyList())
        private set
    var allShops by mutableStateOf<List<Shop>>(emptyList())
        private set
    init {
        fetchShops()
    }
    fun fetchShops() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                allShops = ramenApiService.getShops().shops
                applyFilter()
            } catch (e: Exception) {
                Log.e("ShopListViewModel", "Error fetching shops", e)
                errorMessage = "ショップ一覧の取得に失敗しました"
            } finally {
                isLoading = false
            }
        }
    }
    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        applyFilter()
    }
    fun onSearchSubmit(repository: SearchHistoryRepository) {
        if (searchQuery.isNotBlank()) {
            repository.addHistory(searchQuery)
            loadHistory(repository)
        }
        applyFilter()
    }
    fun onHistorySelected(history: String, repository: SearchHistoryRepository) {
        searchQuery = history
        repository.addHistory(history)
        loadHistory(repository)
        applyFilter()
    }
    fun clearSearch() {
        searchQuery = ""
        applyFilter()
    }
    fun loadHistory(repository: SearchHistoryRepository) {
        searchHistory = repository.getHistory()
    }
    private fun applyFilter() {
        shops = if (searchQuery.isBlank()) {
            allShops
        } else {
            allShops.filter {
                (it.name?.contains(searchQuery, ignoreCase = true) == true) ||
                        (it.id.contains(searchQuery, ignoreCase = true))
            }
        }
    }
    fun toggleFavorite(repository: FavoriteRepository, shopId: String) {
        repository.toggleFavorite(shopId)
    }
}
