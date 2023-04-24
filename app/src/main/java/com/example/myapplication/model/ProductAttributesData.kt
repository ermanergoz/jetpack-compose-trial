package com.example.myapplication.model

data class ProductAttributesData(
    var productAttributesInfo: List<ProductAttributeData> = emptyList()
)

data class ProductAttributeData(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var productListTitle: String = "",
    var name: String = ""
)
