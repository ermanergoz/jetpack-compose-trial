package com.example.myapplication.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson

data class ProductAttributesData(
    var productAttributesInfo: List<ProductAttributeData> = emptyList()
)

data class ProductAttributeData(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var productListTitle: String = "",
    var name: String = "",
    var similarProductsData: SimilarProductsData = SimilarProductsData()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(SimilarProductsData::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(productListTitle)
        parcel.writeString(name)
        parcel.writeParcelable(similarProductsData, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductAttributeData> {
        override fun createFromParcel(parcel: Parcel): ProductAttributeData {
            return ProductAttributeData(parcel)
        }

        override fun newArray(size: Int): Array<ProductAttributeData?> {
            return arrayOfNulls(size)
        }
    }
}

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