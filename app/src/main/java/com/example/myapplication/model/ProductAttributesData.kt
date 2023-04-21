package com.example.myapplication.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

data class ProductAttributesData(
    var productAttributesInfo: List<ProductAttributeData> = emptyList()
)

@Parcelize
data class ProductAttributeData(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var productListTitle: String = "",
    var name: String = "",
    var similarProductsData: SimilarProductsData = SimilarProductsData()
) : Parcelable

class ProductAttributeInfoType : NavType<ProductAttributeData>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ProductAttributeData? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): ProductAttributeData {
        return Gson().fromJson(value, ProductAttributeData::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ProductAttributeData) {
        bundle.putParcelable(key, value)
    }
}