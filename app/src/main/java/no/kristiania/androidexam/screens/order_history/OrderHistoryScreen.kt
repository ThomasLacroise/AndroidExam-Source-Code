package no.kristiania.androidexam.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
// Order history screen //
@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel,
    onBackButtonClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onOrderClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val loading = viewModel.loading.collectAsState()
    val orderHistory = viewModel.orderHistory.collectAsState()

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
// Top bar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to list screen"
                )
            }
            Text(
                modifier = Modifier.padding(4.dp),
                text = "Order history",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onCartClick
                )
                {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping cart"
                    )
                }
                IconButton(
                    onClick = onOrderClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "Orders"
                    )
                }
                IconButton(
                    onClick = onHomeClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home button"
                    )
                }
            }
        }

        // Displays order items in a lazy column
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(orderHistory.value) { orderItem ->
                OrderItem(order = orderItem)

            }
        }
    }
}