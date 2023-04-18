package com.example.myapplication.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ApiHandler
import com.example.myapplication.model.Product
import com.example.myapplication.model.SimilarProductsInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            runCatching {
                kotlin.runCatching {
                    apiHandler.getProductInfo()
                }.onSuccess {
                    _productState.value = it
                }.onFailure {
                    Log.e("getProductInfo", it.message ?: "")
                }
            }

        }
    }

    private fun getSimilarProducts() {
        viewModelScope.launch {
            runCatching {
                kotlin.runCatching {
                    apiHandler.getSimilarProductsInfo()
                }.onSuccess {
                    _similarProductsState.value = it
                }.onFailure {
                    Log.e("getSimilarProducts", it.message ?: "")
                }
            }

        }
    }
}