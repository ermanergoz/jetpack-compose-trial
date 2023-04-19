package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.model.Product
import com.example.myapplication.model.SimilarProductsInfo
import com.example.myapplication.ui.*
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]

        this.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UIState.Error -> onError(uiState.message)
                    UIState.Loading -> onLoading()
                    is UIState.MainUIState -> onMainUIState(
                        uiState.product, uiState.similarProductsInfo
                    )
                }
            }
        }
    }

    private fun onLoading() {
        viewModel.fetchProductData()
    }

    private fun onMainUIState(product: Product, similarProductsInfo: SimilarProductsInfo) {
        setContent {
            MyApplicationTheme {
                val scrollState = rememberScrollState(0)
                Column(modifier = Modifier.background(Background)) {
                    NavigationBar(isScrolled = scrollState.value != 0,
                        productName = product.name,
                        onButtonClick = { showToast("Close button pressed") })
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .weight(weight = 1f, fill = false)
                    ) {
                        ProductCarousel(imageUrls = product.productImages)
                        ProductInfo(product = product) { showToast("A product attribute pressed") }
                        ProductRating(product = product)
                        SimilarProducts(similarProductsInfo = similarProductsInfo)
                    }
                    AddFavorites { showToast("Add favorites button pressed") }
                }
            }
        }
    }

    private fun onError(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
