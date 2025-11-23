package no.kristiania.androidexam.screens.product_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import no.kristiania.androidexam.data.ProductDetails
import no.kristiania.androidexam.ui.theme.MoneyGreen

// Product details screen //
@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    onBackButtonClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onOrderClick: () -> Unit = {}
) {
    val loading = viewModel.loading.collectAsState()
    val productState = viewModel.selectedProductDetails.collectAsState()

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

    val productDetails = productState.value
    if (productDetails == null) {
        Text(text = "Failed to retrieve product details. Selected product details is null! ")
        return
    }
    // Top bar with clickable icons to navigate the app //
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
                modifier = Modifier.padding(8.dp),
                text = "Product details",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = onCartClick)
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
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Displays product image //
        AsyncImage(
            modifier = Modifier
                .width(240.dp)
                .height(230.dp)
                .background(color = Color.White),
            model = productDetails.image,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            contentDescription = "Image of ${productDetails.title}"
        )

        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = productDetails.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))
            // Colored product price button //
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "$${productDetails.price}",
                    color = MoneyGreen
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Category and Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${productDetails.category}",
                    color = Color.Gray
                )
                RatingWithStar(productDetails = productDetails)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Product Description //
            Text(
                text = productDetails.description
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Button to add product to shopping cart //
            Button(
                onClick = {
                    viewModel.addToCart(quantity = 1)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add to cart")
            }
        }
    }
}
// Composable function to print the ratings //
@Composable
fun RatingWithStar(productDetails: ProductDetails) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${productDetails.rating.rate} ",
            color = Color.Gray
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star Icon",
            tint = Color.DarkGray
        )
        Text(
            text = " ${productDetails.rating.count}",
            color = Color.Gray
        )
    }
}