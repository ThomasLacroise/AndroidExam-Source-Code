package no.kristiania.androidexam.screens.order_history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.kristiania.androidexam.data.Order
import no.kristiania.androidexam.data.ProductRepository
// View model for the Order History screen as an object to be accessible globally
object OrderHistoryViewModel: ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _orderHistory = MutableStateFlow<List<Order>>(emptyList())
    val orderHistory = _orderHistory.asStateFlow()

    init {
        loadOrderHistory()
    }

    fun loadOrderHistory() {
        viewModelScope.launch {
            try {
            _loading.value = true
            _orderHistory.value = ProductRepository.getOrderHistory().first()
            } catch (e: Exception) {
                Log.e("OrderHistoryViewModel", "Failed to load order history")
            } finally {
            _loading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}