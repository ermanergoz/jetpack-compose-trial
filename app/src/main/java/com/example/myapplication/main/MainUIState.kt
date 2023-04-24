package com.example.myapplication.main

import com.example.myapplication.model.Product
import com.example.myapplication.model.SimilarProductsData

data class MainUIState(
    val product: Product = Product(),
    val similarProductsData: SimilarProductsData = SimilarProductsData()
)