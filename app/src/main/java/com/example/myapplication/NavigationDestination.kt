package com.example.myapplication

import com.example.myapplication.model.ProductAttribute

sealed class NavigationDestination {
    data class MoreScreen(val route: String = "more") : NavigationDestination()
    data class MainScreen(val route: String = "main") : NavigationDestination()
    data class ProductAttributeScreen(
        val route: String = "product_attribute",
        var productAttribute: ProductAttribute? = null
    ) : NavigationDestination()
}
