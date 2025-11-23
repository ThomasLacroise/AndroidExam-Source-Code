package no.kristiania.androidexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.kristiania.androidexam.data.ProductRepository
import no.kristiania.androidexam.screens.order_history.OrderHistoryScreen
import no.kristiania.androidexam.screens.order_history.OrderHistoryViewModel
import no.kristiania.androidexam.screens.product_cart.ProductCartScreen
import no.kristiania.androidexam.screens.product_cart.ProductCartViewModel
import no.kristiania.androidexam.screens.product_details.ProductDetailsScreen
import no.kristiania.androidexam.screens.product_details.ProductDetailsViewModel
import no.kristiania.androidexam.screens.product_list.ProductListScreen
import no.kristiania.androidexam.screens.product_list.ProductListViewModel
import no.kristiania.androidexam.ui.theme.AndroidExamTheme

class MainActivity : ComponentActivity() {
    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _productCartViewModel: ProductCartViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initializeDatabase(applicationContext)

        setContent {
            AndroidExamTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "productListScreen"
                ) {

                    composable(
                        route = "productListScreen"
                    ) {
                        ProductListScreen(
                            viewModel = _productListViewModel,
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            },
                            onCartClick = {
                                navController.navigate("productCartScreen")
                            },
                            onOrderClick = {
                                navController.navigate("orderHistoryScreen")
                            }
                        )
                    }
                    // Product list screen
                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId = productId)
                        }
                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = {
                                navController.popBackStack()
                            },
                            onCartClick = {
                                navController.navigate("productCartScreen")
                            },
                            onOrderClick = {
                                navController.navigate("OrderHistoryScreen")
                            }
                        )
                    }

                    // Product cart screen to view the added items
                    composable(
                        route = "productCartScreen"
                    ) {
                        ProductCartScreen(
                            viewModel = _productCartViewModel,
                            onBackButtonClick = {
                                navController.popBackStack()
                            },
                            onOrderClick = {
                                navController.navigate("orderHistoryScreen")
                            },
                            onHomeClick = {
                                navController.navigate("productListScreen")
                            })

                    }
                    // Order history screen
                    composable(
                        route = "orderHistoryScreen"
                    ) {
                        OrderHistoryScreen(
                            viewModel = _orderHistoryViewModel,
                            onBackButtonClick = {
                                navController.popBackStack()
                            },
                            onCartClick = {
                                navController.navigate("productCartScreen")
                            },
                            onOrderClick = {
                                navController.navigate("orderHistoryScreen")
                            },
                            onHomeClick = {
                                navController.navigate("productListScreen")
                            }

                        )
                    }
                }
            }
        }
    }
}

