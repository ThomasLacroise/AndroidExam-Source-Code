package no.kristiania.androidexam.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.kristiania.androidexam.data.CartItem
import no.kristiania.androidexam.data.ProductDetails
import no.kristiania.androidexam.data.ProductRepository
// View model for the Product details screen
class ProductDetailsViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedProductDetails = MutableStateFlow<ProductDetails?>(null)
    val selectedProductDetails = _selectedProductDetails.asStateFlow()

    // Function to get products by id
    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProductDetails.value = ProductRepository.getProductDetails(id = productId)
            _loading.value = false
        }

    }
    fun addToCart(quantity: Int) {
        viewModelScope.launch {
            selectedProductDetails.value?.let { productDetails ->
                ProductRepository.addToCart(productDetails, quantity)
            }
        }
    }
}