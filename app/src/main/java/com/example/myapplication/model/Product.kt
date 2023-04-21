package com.example.myapplication.model

import android.os.Parcel
import android.os.Parcelable

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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeStringList(productImages)
        parcel.writeString(gtin)
        parcel.writeString(brandName)
        parcel.writeString(category)
        parcel.writeStringList(leapAttributes)
        parcel.writeInt(reviewCount)
        parcel.writeDouble(ratingValue)
        parcel.writeInt(points)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
