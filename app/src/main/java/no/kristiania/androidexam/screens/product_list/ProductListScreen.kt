package no.kristiania.androidexam.screens.product_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Product list screen //
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onProductClick: (productId: Int) -> Unit = {},
    onCartClick: () -> Unit = {},
    onOrderClick: () -> Unit = {}
) {
    // Calls on functions in the view model //
    val loading = viewModel.loading.collectAsState()
    val searchFilter = viewModel.searchFilter.collectAsState()
    val products = viewModel.filteredProducts.collectAsState()

    if (loading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    // Top bar //
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Products",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onCartClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping cart"
                    )
                }
                IconButton(
                    onClick =  onOrderClick
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Orders"
                    )
                }
            }
        }
        // Search bar //
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = searchFilter.value,
            onValueChange = { text -> viewModel.onFilterTextChanged(text) },
            textStyle = MaterialTheme.typography.titleMedium,
            label = { Text(text = "Search for a product") },
        )

        Divider()

        // Lazy column displaying product items //
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(products.value) { product ->
                ProductItem(
                    product = product,
                    onClick = {
                        onProductClick(product.id)
                    }
                )
            }
        }
    }
}