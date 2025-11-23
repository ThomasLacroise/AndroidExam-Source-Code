package no.kristiania.androidexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Cart item information that displays-
// relevant information //
@Entity
data class CartItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val category: String,
    val price: Float,
    val quantity: Int
)
