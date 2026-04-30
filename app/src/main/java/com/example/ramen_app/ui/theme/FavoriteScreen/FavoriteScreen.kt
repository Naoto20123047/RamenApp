package com.example.ramen_app.ui.theme.ShopListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ramen_app.ui.theme.RamenAPI.Shop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    onShopClick: (String) -> Unit,
    onBack: () -> Unit,
    vm: ShopListViewModel = viewModel()
) {
    val context = LocalContext.current
    val favoriteRepository = remember { FavoriteRepository(context) }
    val favoriteIds by favoriteRepository.favoriteIds.collectAsState(initial = emptySet<String>())
    val favoriteShops = vm.allShops.filter { it.id in favoriteIds }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("お気に入り") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (favoriteShops.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("お気に入りはまだありません", color = MaterialTheme.colorScheme.outline)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteShops, key = { it.id }) { shop ->
                    ShopCard(
                        shop = shop,
                        isFavorite = true,
                        onFavoriteClick = { vm.toggleFavorite(favoriteRepository, shop.id) },
                        onClick = { onShopClick(shop.id) }
                    )
                }
            }
        }
    }
}
