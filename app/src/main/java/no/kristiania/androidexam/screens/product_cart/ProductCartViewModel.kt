package no.kristiania.androidexam.screens.product_cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.kristiania.androidexam.data.CartItem
import no.kristiania.androidexam.data.Order
import no.kristiania.androidexam.data.ProductRepository
import no.kristiania.androidexam.screens.order_history.OrderHistoryViewModel.loadOrderHistory
import java.util.Date
import kotlin.math.max

object ProductCartViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount = _cartItemCount.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice = _totalPrice.asStateFlow()

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            _loading.value = true
            _cartItems.value = ProductRepository.getCartItems().first()
            _cartItemCount.value = calculateCartItemCount(_cartItems.value)
            _totalPrice.value = calculateTotalPrice(_cartItems.value)
            _loading.value = false
        }
    }


    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            ProductRepository.removeFromCart(cartItem)
            loadCartItems()
            _cartItemCount.value -= cartItem.quantity
        }
    }
    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            ProductRepository.increaseQuantity(cartItem)
            loadCartItems()
            _cartItemCount.value += 1
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            ProductRepository.decreaseQuantity(cartItem)
            loadCartItems()
            _cartItemCount.value = max(_cartItemCount.value - 1, 0)
        }
    }

    private fun calculateCartItemCount(cartItems: List<CartItem>): Int {
        return cartItems.sumOf { it.quantity }
    }

    private fun calculateTotalPrice(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { it.price.toDouble() * it.quantity }
    }

    fun placeOrder() {
        viewModelScope.launch {
            try {

            val cartItems = _cartItems.value
            val totalPrice = calculateTotalPrice(cartItems)

            val order = Order(
                orderDate = Date(),
                orderItems = cartItems.map { cartItem ->
                    CartItem(
                        id = cartItem.id,
                        title = cartItem.title,
                        price = cartItem.price,
                        category = cartItem.category,
                        quantity = cartItem.quantity,
                        image = cartItem.image

                    )
                },
                totalPrice = totalPrice
            )

            ProductRepository.placeOrder(order)
            clearCart()
            } catch (e: Exception) {
                Log.d("PlaceOrder", "Error placing order")
            }
            loadCartItems()
            loadOrderHistory()
        }
    }

    private fun clearCart() {
        viewModelScope.launch {
            ProductRepository.clearCart()
        }
    }
}