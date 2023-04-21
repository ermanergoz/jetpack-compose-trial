package com.example.myapplication.model

import android.os.Parcel
import android.os.Parcelable

data class SimilarProductsData(
    var similarProducts: List<Product> = emptyList()
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Product)!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(similarProducts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimilarProductsData> {
        override fun createFromParcel(parcel: Parcel): SimilarProductsData {
            return SimilarProductsData(parcel)
        }

        override fun newArray(size: Int): Array<SimilarProductsData?> {
            return arrayOfNulls(size)
        }
    }
}
