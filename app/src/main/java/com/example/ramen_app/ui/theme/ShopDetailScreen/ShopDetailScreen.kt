package com.example.ramen_app.ui.theme.ShopDetailScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDetailScreen(
    onBack: () -> Unit,
    vm: ShopDetailViewModel = viewModel()
) {
    val context = LocalContext.current
    val memoRepository = remember { MemoRepository(context) }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
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
                    var memo by remember {
                        mutableStateOf(memoRepository.getMemo(shop.id))
                    }
                    var rating by remember {
                        mutableIntStateOf(memoRepository.getRating(shop.id))
                    }
                    var isEditing by remember { mutableStateOf(false) }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        shop.thumbnailUrl?.let { url ->
                            item {
                                AsyncImage(
                                    model = url,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(260.dp)
                                )
                            }
                        }
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = shop.name ?: shop.id,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "ID: ${shop.id}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                                Button(
                                    onClick = {
                                        val query = Uri.encode(shop.name ?: shop.id)
                                        val uri = Uri.parse("geo:0,0?q=$query")
                                        val intent = Intent(Intent.ACTION_VIEW, uri)
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Place,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text("Google Maps で経路案内")
                                }
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
                                    modifier = Modifier.padding(
                                        start = 16.dp, top = 16.dp, bottom = 8.dp
                                    )
                                )
                            }
                            items(extraPhotos) { photo ->
                                AsyncImage(
                                    model = photo.url,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp)
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }
                        item {
                            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                            MemoSection(
                                shopId = shop.id,
                                memo = memo,
                                rating = rating,
                                isEditing = isEditing,
                                onMemoChange = { memo = it },
                                onRatingChange = { rating = it },
                                onEditClick = { isEditing = true },
                                onSaveClick = {
                                    memoRepository.saveMemo(shop.id, memo)
                                    memoRepository.saveRating(shop.id, rating)
                                    isEditing = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun MemoSection(
    shopId: String,
    memo: String,
    rating: Int,
    isEditing: Boolean,
    onMemoChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "ユーザーメモ",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "評価",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
            repeat(5) { index ->
                val filled = index < rating
                IconButton(
                    onClick = {
                        if (isEditing) onRatingChange(index + 1)
                        else onEditClick()
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (filled) Icons.Filled.Star else Icons.Filled.StarBorder,
                        contentDescription = "${index + 1}点",
                        tint = if (filled) Color(0xFFFFC107) else MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
        if (isEditing) {
            OutlinedTextField(
                value = memo,
                onValueChange = onMemoChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("このお店のメモを入力...") },
                minLines = 3,
                maxLines = 6,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("保存する")
            }
        } else {
            if (memo.isBlank()) {
                OutlinedButton(
                    onClick = onEditClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("メモを追加する")
                }
            } else {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = memo,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                TextButton(
                    onClick = onEditClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("編集する")
                }
            }
        }
    }
}
