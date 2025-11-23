package no.kristiania.androidexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Product details entity displaying the wanted information from the API
@Entity
data class ProductDetails(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val image: String,
    val rating: ProductRating // calling on the product rate
)
// Another entity for the product rating since the rating is display two different numbers
@Entity
data class ProductRating(
    val rate: Float,
    val count: Int
)


