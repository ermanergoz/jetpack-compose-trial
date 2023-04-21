package com.example.myapplication.ui

import com.example.myapplication.model.Product
import com.example.myapplication.model.ProductAttributesData
import com.example.myapplication.model.SimilarProductsData

sealed class UIState {
    object Loading : UIState()
    data class MainUIState(
        val product: Product,
        val similarProductsData: SimilarProductsData,
        val productAttributesInfo: ProductAttributesData
    ) : UIState()

    data class Error(val message: String) : UIState()
}
