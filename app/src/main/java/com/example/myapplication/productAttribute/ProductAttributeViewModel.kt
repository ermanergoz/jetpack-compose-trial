package com.example.myapplication.productAttribute

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ApiHandler
import com.example.myapplication.model.ProductAttributesData
import com.example.myapplication.model.SimilarProductsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductAttributeViewModel(private val apiHandler: ApiHandler) : ViewModel() {
    private val _prodAttrUIState: MutableStateFlow<ProdAttrUIState> =
        MutableStateFlow(ProdAttrUIState())
    val prodAttrUIState: StateFlow<ProdAttrUIState> = _prodAttrUIState

    init {
        fetchProductAttrData()
    }

    private fun fetchProductAttrData() {
        viewModelScope.launch {
            var productAttributesData = ProductAttributesData()
            var similarAttrProductsData = SimilarProductsData()
            runCatching {
                kotlin.runCatching {
                    productAttributesData = apiHandler.getProductAttributesInfo()
                    similarAttrProductsData = apiHandler.getSimilarProductsInfo()
                }.onSuccess {
                    _prodAttrUIState.value = ProdAttrUIState(productAttributesData, similarAttrProductsData)
                }.onFailure {
                    it.message?.let { it1 -> Log.e("fetchProductAttrData", it1) }
                }
            }
        }
    }
}
