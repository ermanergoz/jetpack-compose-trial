package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.model.*
import com.example.myapplication.ui.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]

        this.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UIState.Error -> onError(uiState.message)
                    is UIState.Loading -> onLoading()
                    is UIState.MainUIState -> onMainUIState(
                        uiState.product, uiState.productAttributesInfo, uiState.similarProductsData
                    )
                }
            }
        }

        viewModel.navigateTo.observe(this) { navigateTo ->
            when (navigateTo) {
                is NavigationDestination.MoreScreen -> {
                    navController.navigate(navigateTo.route)
                }
                is NavigationDestination.MainScreen -> {
                    navController.navigate(navigateTo.route)
                }
                is NavigationDestination.ProductAttributeScreen -> {
                    val json = Uri.encode(Gson().toJson(navigateTo.productAttributeData))
                    navController.navigate(navigateTo.route + "/$json")
                }
            }
        }
    }

    private fun onLoading() {
        viewModel.fetchProductData()
    }

    private fun onMainUIState(
        product: Product,
        productAttributesInfo: ProductAttributesData,
        similarProductsData: SimilarProductsData
    ) {
        Log.e("State", "Main screen")
        setContent {
            MyApplicationTheme {
                navController = rememberNavController()
                NavHost(navController, NavigationDestination.MainScreen().route) {
                    composable(NavigationDestination.MainScreen().route) {
                        MainScreen(
                            product, productAttributesInfo, similarProductsData
                        ) { viewModel.handleButtonClicks(it) }
                    }
                    composable(NavigationDestination.MoreScreen().route) {
                        MoreInfoComposable(product = product) { viewModel.handleButtonClicks(it) }
                    }
                    composable(
                        NavigationDestination.ProductAttributeScreen().route + SEPARATOR + NAV_ARGUMENT_PLACEHOLDER,
                        arguments = listOf(navArgument(NAV_ARGUMENT_NAME) {
                            type = ProductAttributeInfoType()
                        })
                    ) {
                        val post =
                            it.arguments?.getParcelable<ProductAttributeData>(NAV_ARGUMENT_NAME)
                        post?.let { attr ->
                            ProductAttributeComposable(productAttributeData = attr) { buttonAction ->
                                viewModel.handleButtonClicks(buttonAction)
                            }
                        }
                    }
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

const val SEPARATOR: String = "/"
const val NAV_ARGUMENT_NAME: String = "productAttributeInfo"
const val NAV_ARGUMENT_PLACEHOLDER: String = "{productAttributeInfo}"
