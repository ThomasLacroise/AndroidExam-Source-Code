package no.kristiania.androidexam.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import no.kristiania.androidexam.data.Order
import no.kristiania.androidexam.ui.theme.MoneyGreen
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun OrderItem(
    order: Order
) {
    val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.orderDate)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))

            order.orderItems.forEach { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${product.id} - ${product.title}",
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Total: $${String.format("%.2f", order.totalPrice)}",
                color = MoneyGreen
            )
        }
    }
}