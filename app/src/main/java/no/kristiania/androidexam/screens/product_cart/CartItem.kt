package no.kristiania.androidexam.screens.product_cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import no.kristiania.androidexam.data.CartItem
import no.kristiania.androidexam.ui.theme.MoneyGreen

@Composable
fun CartItem(
    cartItem: CartItem,
    increaseQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
    removeFromCart: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12)
            )
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .padding(6.dp)
                .background(color = Color.Gray),
            model = cartItem.image,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of ${cartItem.title}"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            ) {
            Text(
                text = cartItem.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = cartItem.category,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$${cartItem.price}",
                color = MoneyGreen
            )
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 3.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            IconButton(onClick = increaseQuantity) {
                Icon(imageVector = Icons.Default.AddCircle,
                     contentDescription = "Increase quantity"
                )
            }

            IconButton(onClick = decreaseQuantity) {
                Icon(imageVector = Icons.Default.Clear,
                     contentDescription = "Decrease quantity"
                )
            }

            Text(
                text = cartItem.quantity.toString(),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.CenterVertically)
            )

            IconButton(onClick = removeFromCart) {
                Icon(imageVector = Icons.Default.Delete,
                     contentDescription = "Remove item"
                )
            }
        }
    }
}