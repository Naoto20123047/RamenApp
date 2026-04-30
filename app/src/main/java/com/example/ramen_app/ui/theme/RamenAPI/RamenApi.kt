package com.example.ramen_app.ui.theme.RamenAPI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class Photo(
    val name: String,
    val url: String,
    val width: Int,
    val height: Int,
    val authorId: String?
)
data class Shop(
    val id: String,
    val name: String?,
    val photos: List<Photo>?
) {
    val thumbnailUrl: String? get() = photos?.firstOrNull()?.url
}
data class PageInfo(
    val currentPage: Int,
    val lastPage: Int,
    val nextPage: Int?,
    val prevPage: Int?
)
data class ShopsResponse(
    val shops: List<Shop>,
    val totalCount: Int,
    val pageInfo: PageInfo
)
data class ShopResponse(val shop: Shop)
interface RamenApiService {
    @GET("shops")
    suspend fun getShops(
        @Query("page") page: Int = 1,
        @Query("perPage") perPage: Int = 20
    ): ShopsResponse
    @GET("shops/{shopId}")
    suspend fun getShop(@Path("shopId") shopId: String): ShopResponse
}
val ramenApiService: RamenApiService = Retrofit.Builder()
    .baseUrl("https://ramen-api.dev/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(RamenApiService::class.java)
