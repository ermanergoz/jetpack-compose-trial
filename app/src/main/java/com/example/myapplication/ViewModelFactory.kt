package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.model.ApiHandlerImpl
import com.example.myapplication.main.MainViewModel
import com.example.myapplication.productAttribute.ProductAttributeViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(MainViewModel::class.java) -> MainViewModel(ApiHandlerImpl())
            isAssignableFrom(ProductAttributeViewModel::class.java) -> ProductAttributeViewModel(ApiHandlerImpl())
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
