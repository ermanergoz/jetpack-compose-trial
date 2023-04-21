package com.example.myapplication.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val apiHandler: ApiHandler) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState> =
        MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private val _navigateTo = MutableLiveData<NavigationDestination>()
    val navigateTo: LiveData<NavigationDestination> = _navigateTo

    fun fetchProductData() {
        viewModelScope.launch {
            var product = Product()
            var similarProductsData = SimilarProductsData()
            var productAttributesInfo = ProductAttributesData()
            runCatching {
                kotlin.runCatching {
                    product = apiHandler.getProductInfo()
                    similarProductsData = apiHandler.getSimilarProductsInfo()
                    productAttributesInfo = apiHandler.getProductAttributesInfo()
                }.onSuccess {
                    _uiState.value = UIState.MainUIState(product, similarProductsData, productAttributesInfo)
                }.onFailure {
                    _uiState.value = UIState.Error(it.message ?: "")
                }
            }
        }
    }

    fun handleButtonClicks(buttonAction: ButtonAction) {
        when (buttonAction) {
            is ButtonAction.CloseButton -> {
                Log.d("Button:", "Close button pressed")
            }
            is ButtonAction.FavoriteButton -> {
                Log.d("Button:", "Add favorites button pressed")
            }
            is ButtonAction.NavigateButton -> {
                _navigateTo.value = buttonAction.navigateTo
            }
        }
    }
}
