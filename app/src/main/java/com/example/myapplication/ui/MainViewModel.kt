package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ApiHandler
import com.example.myapplication.model.Product
import com.example.myapplication.model.SimilarProductsInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val apiHandler: ApiHandler) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState> =
        MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    fun fetchProductData() {
        viewModelScope.launch {
            var product = Product()
            var similarProductsInfo = SimilarProductsInfo()
            runCatching {
                kotlin.runCatching {
                    product = apiHandler.getProductInfo()
                    similarProductsInfo = apiHandler.getSimilarProductsInfo()
                }.onSuccess {
                    _uiState.value = UIState.MainUIState(product, similarProductsInfo)
                }.onFailure {
                    _uiState.value = UIState.Error(it.message ?: "")
                }
            }
        }
    }
}
