package com.example.ramen_app.ui.theme.NewsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class NewsArticle(
    val title: String,
    val summary: String,
    val date: String
)
val mockNews = listOf(
    NewsArticle(
        title = "2024年最新！東京で話題のラーメン店5選",
        summary = "東京都内で今最も注目を集めているラーメン店をご紹介します。個性豊かなスープと独自のトッピングが魅力です。",
        date = "2024年12月1日"
    ),
    NewsArticle(
        title = "ラーメン業界トレンド：魚介系スープが人気急上昇",
        summary = "近年、魚介系のスープを使ったラーメンが全国的に人気を集めています。その背景と注目店舗を解説します。",
        date = "2024年11月20日"
    ),
    NewsArticle(
        title = "家系ラーメンの歴史と文化",
        summary = "横浜発祥の家系ラーメンはなぜ全国に広まったのか。その歴史と文化的背景を深掘りします。",
        date = "2024年11月10日"
    ),
    NewsArticle(
        title = "ラーメンの健康的な食べ方とは？",
        summary = "ラーメンをヘルシーに楽しむための工夫や選び方のポイントを管理栄養士が解説します。",
        date = "2024年10月25日"
    ),
    NewsArticle(
        title = "冬に食べたい！濃厚味噌ラーメン特集",
        summary = "寒い季節にぴったりの濃厚味噌ラーメン。全国の名店から厳選した5店舗をご紹介します。",
        date = "2024年10月15日"
    )
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ラーメンニュース") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mockNews) { article ->
                NewsCard(article = article)
            }
        }
    }
}
@Composable
private fun NewsCard(article: NewsArticle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = article.date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 2
            )
        }
    }
}
