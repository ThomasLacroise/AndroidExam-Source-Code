package no.kristiania.androidexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Order entity information
@Entity
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val orderDate: Date,
    val orderItems: List<CartItem>,
    val totalPrice: Double
)
