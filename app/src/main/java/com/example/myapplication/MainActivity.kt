package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val productState = remember { mutableStateOf(Product()) }
            getProductInfo { productRes -> productRes?.let { productState.value = productRes } }
            MyApplicationTheme {
                val scrollState = rememberScrollState(0)
                Column(modifier = Modifier.background(Background)) {
                    NavigationBar(
                        isScrolled = scrollState.value != 0,
                        productName = productState.value.name,
                        onButtonClick = { showToast("Close button pressed") }
                    )
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .weight(weight = 1f, fill = false)
                    ) {
                        ProductCarousel(imageUrls = productState.value.productImages)
                        ProductInfo(product = productState.value) { showToast("A product attribute pressed") }
                        ProductRating(product = productState.value)
                        SimilarProducts()
                    }
                    AddFavorites { showToast("Add favorites button pressed") }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getProductInfo(callback: (product: Product?) -> Unit) {
        AndroidNetworking.get(URL).build()
            .getAsObject(Product::class.java, object : ParsedRequestListener<Product> {
                override fun onResponse(response: Product) {
                    callback(response)
                }

                override fun onError(anError: ANError?) {
                    callback(null)
                    anError?.errorDetail?.let {
                        Log.e("Api Error", it)
                        showToast(it)
                    }
                }
            })
    }
}

const val URL = "https://private-0c5632-yusuf6.apiary-mock.com/product"