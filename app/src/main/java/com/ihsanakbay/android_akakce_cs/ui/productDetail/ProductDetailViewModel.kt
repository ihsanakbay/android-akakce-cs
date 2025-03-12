package com.ihsanakbay.android_akakce_cs.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihsanakbay.android_akakce_cs.data.models.Product
import com.ihsanakbay.android_akakce_cs.data.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModel : ViewModel() {
    private val repository = ProductRepository()

    private var _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchProductDetail(productId: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = repository.getProductDetail(productId)

                    withContext(Dispatchers.Main) {
                        if (response != null) {
                            _product.value = response
                        } else {
                            _error.value = "Product not found"
                        }
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                    _isLoading.value = false
                }
            }
        }
    }
}