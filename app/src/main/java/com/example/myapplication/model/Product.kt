package com.example.myapplication.model

data class Product(
    var name: String = "",
    var description: String = "",
    var productImages: List<String> = emptyList(),
    var gtin: String = "",
    var brandName: String = "",
    var category: String = "",
    var leapAttributes: List<String> = emptyList(),
    var reviewCount: Int = 0,
    var ratingValue: Double = 0.0,
    var points: Int = 0
)
