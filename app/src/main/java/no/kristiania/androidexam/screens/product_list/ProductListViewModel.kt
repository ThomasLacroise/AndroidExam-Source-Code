package no.kristiania.androidexam.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.kristiania.androidexam.data.Product
import no.kristiania.androidexam.data.ProductRepository
// Product list view model //
class ProductListViewModel: ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _searchFilter = MutableStateFlow("")
    val searchFilter = _searchFilter.asStateFlow()

    val filteredProducts = combine(products, searchFilter) { product, filterText ->
        product.filter { it.title.startsWith(filterText, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _products.value = ProductRepository.getProducts()
            _loading.value = false
        }
    }

    fun onFilterTextChanged(text: String) {
        _searchFilter.value = text
    }
}