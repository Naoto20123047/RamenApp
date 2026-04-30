package com.example.ramen_app.ui.theme.ShopDetailScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ramen_app.ui.theme.RamenAPI.Shop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDetailScreen(
    onBack: () -> Unit,
    vm: ShopDetailViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(vm.shop?.name ?: "読み込み中...") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
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
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            when {
                vm.isLoading -> CircularProgressIndicator()
                vm.errorMessage != null -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(vm.errorMessage!!, color = MaterialTheme.colorScheme.error)
                    Button(onClick = { vm.fetchShop() }) { Text("再試行") }
                }
                vm.shop != null -> {
                    val shop = vm.shop!!
                    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
                        shop.thumbnailUrl?.let { url ->
                            item {
                                AsyncImage(
                                    model = url,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth().height(260.dp)
                                )
                            }
                        }
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = shop.name ?: shop.id, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                                Text(text = "ID: ${shop.id}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                            }
                            HorizontalDivider()
                        }
                        val extraPhotos = shop.photos.orEmpty().drop(1)
                        if (extraPhotos.isNotEmpty()) {
                            item {
                                Text(
                                    text = "写真（${shop.photos?.size ?: 0}枚）",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                                )
                            }
                            items(extraPhotos) { photo ->
                                AsyncImage(
                                    model = photo.url,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth().height(220.dp).padding(horizontal = 16.dp, vertical = 4.dp).clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
