package com.example.myapplication.model

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener

class ApiHandler {
    fun getProductInfo(callback: (product: Product?) -> Unit) {
        AndroidNetworking.get(URL).build()
            .getAsObject(Product::class.java, object : ParsedRequestListener<Product> {
                override fun onResponse(response: Product) {
                    callback(response)
                }

                override fun onError(anError: ANError?) {
                    callback(null)
                    anError?.errorDetail?.let {
                        Log.e("Api Error", it)
                        //showToast(it)
                    }
                }
            })
    }
}

const val URL = "https://private-0c5632-yusuf6.apiary-mock.com/product"