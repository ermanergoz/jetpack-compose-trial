package com.example.myapplication.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ApiHandler
import com.example.myapplication.model.Product
import com.example.myapplication.model.SimilarProductsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val apiHandler: ApiHandler) : ViewModel() {
    private val _mainUIState: MutableStateFlow<MainUIState> =
        MutableStateFlow(MainUIState())
    val mainUIState: StateFlow<MainUIState> = _mainUIState

    init {
        fetchProductData()
    }

    private fun fetchProductData() {
        viewModelScope.launch {
            var product = Product()
            var similarProductsData = SimilarProductsData()
            runCatching {
                kotlin.runCatching {
                    product = apiHandler.getProductInfo()
                    similarProductsData = apiHandler.getSimilarProductsInfo()
                }.onSuccess {
                    _mainUIState.value =
                        MainUIState(product, similarProductsData)
                }.onFailure {
                    it.message?.let { it1 -> Log.e("fetchProductData", it1) }
                }
            }
        }
    }
}
