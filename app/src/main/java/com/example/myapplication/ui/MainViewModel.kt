package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ApiHandler
import com.example.myapplication.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _productState = MutableStateFlow(Product())
    val productState: StateFlow<Product> get() = _productState

    fun getProductInfo() {
        ApiHandler().getProductInfo { productRes ->
            _productState.value = productRes ?: Product()
        }
    }
}