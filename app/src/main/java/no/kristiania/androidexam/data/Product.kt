package no.kristiania.androidexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity class for the product list screen items
@Entity
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val category: String,
    val image: String
)

