package com.example.myapplication.model

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException

class ApiHandler {
    fun getProductInfo(callback: (product: Product?) -> Unit) {
        AndroidNetworking.get(PRODUCT_INFO_URL).build()
            .getAsObject(Product::class.java, object : ParsedRequestListener<Product> {
                override fun onResponse(response: Product) {
                    callback(response)
                }

                override fun onError(anError: ANError?) {
                    callback(null)
                    anError?.errorDetail?.let {
                        Log.e("Api Error", it)
                    }
                }
            })
    }

    fun getSimilarProductsInfo(callback: (similarProductsInfo: SimilarProductsInfo?) -> Unit) {
        AndroidNetworking.get(SIMILAR_PRODUCTS_URL).build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    try {
                        callback(
                            SimilarProductsInfo(
                                Gson().fromJson(
                                    response.toString(), Array<Product>::class.java
                                ).toList()
                            )
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError?) {
                    callback(null)
                    anError?.errorDetail?.let {
                        Log.e("Similar product Api Error", it)
                    }
                }
            })
    }

}

const val PRODUCT_INFO_URL = "https://private-0c5632-yusuf6.apiary-mock.com/product"
const val SIMILAR_PRODUCTS_URL = "https://private-0c5632-yusuf6.apiary-mock.com/product/similar"
