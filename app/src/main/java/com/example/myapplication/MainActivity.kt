package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.main.MainScreen
import com.example.myapplication.main.MainViewModel
import com.example.myapplication.model.ButtonAction
import com.example.myapplication.model.ProductAttribute
import com.example.myapplication.more.MoreInfoComposable
import com.example.myapplication.productAttribute.ProductAttributeComposable
import com.example.myapplication.productAttribute.ProductAttributeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                navController = rememberNavController()
                NavHost(navController, NavigationDestination.MainScreen().route) {
                    val mainViewModel = ViewModelProvider(
                        this@MainActivity, ViewModelFactory()
                    )[MainViewModel::class.java]
                    composable(NavigationDestination.MainScreen().route) {
                        MainScreen(viewModel = mainViewModel) { buttonAction: ButtonAction ->
                            handleButtonAction(buttonAction)
                        }
                    }
                    composable(NavigationDestination.MoreScreen().route) {
                        MoreInfoComposable(viewModel = mainViewModel) {
                            handleButtonAction(it)
                        }
                    }
                    composable(
                        NavigationDestination.ProductAttributeScreen().route + SEPARATOR + NAV_ARGUMENT_PLACEHOLDER,
                        arguments = listOf(navArgument(NAV_ARGUMENT_NAME) {
                            type = NavType.EnumType(ProductAttribute::class.java)
                            nullable = false
                        })
                    ) { backStackEntry ->
                        val productAttrViewModel = ViewModelProvider(
                            this@MainActivity, ViewModelFactory()
                        )[ProductAttributeViewModel::class.java]
                        val arguments = requireNotNull(backStackEntry.arguments)

                        @Suppress("DEPRECATION") val productAttribute =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                arguments.getSerializable(
                                    NAV_ARGUMENT_NAME, ProductAttribute::class.java
                                )
                            } else {
                                arguments.getSerializable(NAV_ARGUMENT_NAME) as? ProductAttribute
                            }

                        productAttribute?.let {
                            ProductAttributeComposable(viewModel = productAttrViewModel,
                                productAttribute = it,
                                onButtonClicked = { buttonAction ->
                                    handleButtonAction(buttonAction)
                                })
                        }
                    }
                }
            }
        }
    }

    private fun handleButtonAction(buttonAction: ButtonAction) {
        when (buttonAction) {
            is ButtonAction.NavigateButton -> navigateToDestination(buttonAction.navigateTo)
            ButtonAction.CloseButton -> navController.popBackStack()
            ButtonAction.FavoriteButton -> Log.e("ButtonAction", "Favorite button")
        }
    }

    private fun navigateToDestination(destination: NavigationDestination) {
        when (destination) {
            is NavigationDestination.MoreScreen -> navController.navigate(destination.route)
            is NavigationDestination.MainScreen -> Log.d("Navigate to", destination.route)
            is NavigationDestination.ProductAttributeScreen ->
                navController.navigate(destination.route + SEPARATOR + destination.productAttribute)
        }
    }
}

const val SEPARATOR: String = "/"
const val NAV_ARGUMENT_NAME: String = "productAttribute"
const val NAV_ARGUMENT_PLACEHOLDER: String = "{productAttribute}"
