package com.example.myapplication.ui

import com.example.myapplication.model.ProductAttributeData

sealed class NavigationDestination {
    data class MoreScreen(val route: String = "more") : NavigationDestination()
    data class MainScreen(val route: String = "main") : NavigationDestination()
    data class ProductAttributeScreen(
        val route: String = "product_attribute",
        var productAttributeData: ProductAttributeData = ProductAttributeData()
    ) : NavigationDestination()
}
