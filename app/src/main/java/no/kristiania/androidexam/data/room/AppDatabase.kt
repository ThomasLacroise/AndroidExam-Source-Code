package no.kristiania.androidexam.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import no.kristiania.androidexam.data.Product
import no.kristiania.androidexam.data.ProductDetails
import androidx.room.TypeConverters
import no.kristiania.androidexam.data.CartItem
import no.kristiania.androidexam.data.Order

@Database(
    entities = [Product::class, ProductDetails::class, CartItem::class, Order::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productDetailsDao(): ProductDetailsDao
    abstract fun productCartDao(): ProductCartDao
    abstract fun orderHistoryDao(): OrderHistoryDao
}