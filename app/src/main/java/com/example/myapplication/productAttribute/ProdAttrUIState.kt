package com.example.myapplication.productAttribute

import com.example.myapplication.model.ProductAttributesData
import com.example.myapplication.model.SimilarProductsData

data class ProdAttrUIState(
    val productAttributesData: ProductAttributesData = ProductAttributesData(),
    val similarAttrProductsData: SimilarProductsData = SimilarProductsData()
)
