package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.*
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            val productState by viewModel.productState.collectAsState()
            val similarProductsState by viewModel.similarProductsState.collectAsState()
            viewModel.fetchProductData()

            MyApplicationTheme {
                val scrollState = rememberScrollState(0)
                Column(modifier = Modifier.background(Background)) {
                    NavigationBar(isScrolled = scrollState.value != 0,
                        productName = productState.name,
                        onButtonClick = { showToast("Close button pressed") })
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .weight(weight = 1f, fill = false)
                    ) {
                        ProductCarousel(imageUrls = productState.productImages)
                        ProductInfo(product = productState) { showToast("A product attribute pressed") }
                        ProductRating(product = productState)
                        SimilarProducts(similarProductsInfo = similarProductsState)
                    }
                    AddFavorites { showToast("Add favorites button pressed") }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
