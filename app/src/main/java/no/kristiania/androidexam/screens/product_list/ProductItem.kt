package no.kristiania.androidexam.screens.product_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import no.kristiania.androidexam.data.Product
import no.kristiania.androidexam.ui.theme.MoneyGreen

// Styling for the product item in list screen //
@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12)
            )
            .background(color = Color.White)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Log.d("ProductItem", "Image URL: ${product.image}")

        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .padding(6.dp)
                .background(color = Color.Gray),
            model = product.image,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of ${product.title}"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,

        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.category,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$${product.price}",
                color = MoneyGreen
            )
        }
    }
}