package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ApiHandler
import com.example.myapplication.model.Product
import com.example.myapplication.model.SimilarProductsInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val apiHandler = ApiHandler()

    private val _productState = MutableStateFlow(Product())
    val productState: StateFlow<Product> get() = _productState

    private val _similarProductsState = MutableStateFlow(SimilarProductsInfo())
    val similarProductsState: StateFlow<SimilarProductsInfo> get() = _similarProductsState

    fun fetchProductData() {
        getProductInfo()
        getSimilarProducts()
    }

    private fun getProductInfo() {
        apiHandler.getProductInfo { productRes ->
            _productState.value = productRes ?: Product()
        }
    }

    private fun getSimilarProducts() {
        apiHandler.getSimilarProductsInfo { productsRes ->
            _similarProductsState.value = productsRes ?: SimilarProductsInfo()
        }
    }
}