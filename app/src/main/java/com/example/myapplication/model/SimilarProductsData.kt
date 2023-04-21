package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimilarProductsData(
    var similarProducts: List<Product> = emptyList()
): Parcelable
