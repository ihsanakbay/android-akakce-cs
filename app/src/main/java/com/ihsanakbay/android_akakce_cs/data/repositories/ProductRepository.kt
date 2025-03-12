package com.ihsanakbay.android_akakce_cs.data.repositories

import com.ihsanakbay.android_akakce_cs.data.api.RetrofitClient
import com.ihsanakbay.android_akakce_cs.data.models.Product
import com.ihsanakbay.android_akakce_cs.utils.Constants
import java.util.Collections.emptyList

class ProductRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getProducts(): List<Product> {
        val response = apiService.getProducts()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getHorizontalProducts(): List<Product> {
        val response = apiService.getHorizontalProducts(Constants.HORIZONTAL_PRODUCTS_LIMIT)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getProductDetail(productId: Int): Product? {
        val response = apiService.getProductDetail(productId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}