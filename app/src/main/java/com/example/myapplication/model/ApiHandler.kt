package com.example.myapplication.model

interface ApiHandler {
    suspend fun getProductInfo(): Product
    suspend fun getSimilarProductsInfo(): SimilarProductsData
    suspend fun getProductAttributesInfo(): ProductAttributesData
}
