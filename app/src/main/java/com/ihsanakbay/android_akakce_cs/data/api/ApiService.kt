package com.ihsanakbay.android_akakce_cs.data.api

import com.ihsanakbay.android_akakce_cs.data.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products")
    suspend fun getHorizontalProducts(@Query("limit") limit: Int): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): Response<Product>
}