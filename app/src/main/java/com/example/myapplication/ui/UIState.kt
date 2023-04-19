package com.example.myapplication.ui

import com.example.myapplication.model.Product
import com.example.myapplication.model.SimilarProductsInfo

sealed class UIState {
    object Loading : UIState()
    data class MainUIState(val product: Product, val similarProductsInfo: SimilarProductsInfo) :
        UIState()

    data class Error(val message: String) : UIState()
}
