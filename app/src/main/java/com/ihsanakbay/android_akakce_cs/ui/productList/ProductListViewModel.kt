package com.ihsanakbay.android_akakce_cs.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanakbay.android_akakce_cs.data.models.Product
import com.ihsanakbay.android_akakce_cs.data.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _horizontalProducts = MutableLiveData<List<Product>>()
    val horizontalProducts: LiveData<List<Product>> = _horizontalProducts

    private val _verticalProducts = MutableLiveData<List<Product>>()
    val verticalProducts: LiveData<List<Product>> = _verticalProducts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val horizontalProductsResult = repository.getHorizontalProducts()
                    val verticalProductsResult = repository.getProducts()

                    withContext(Dispatchers.Main) {
                        _horizontalProducts.value = horizontalProductsResult
                        _verticalProducts.value = verticalProductsResult
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message ?: "Unknown error"
                    _isLoading.value = false
                }
            }
        }
    }

    fun refreshProducts() {
        fetchProducts()
    }
}