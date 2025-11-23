package no.kristiania.androidexam.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.kristiania.androidexam.data.CartItem
import no.kristiania.androidexam.data.ProductRating
import java.util.Date

// Converts the rating and the cart items to the order history
class Converters {

    @TypeConverter
    fun fromProductRating(productRating: ProductRating?): String? {
        return productRating?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toProductRating(value: String?): ProductRating? {
        return value?.let { Gson().fromJson(it, ProductRating::class.java) }
    }

    @TypeConverter
    fun fromDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromCartItemJson(json: String): List<CartItem> {
        val type = object : TypeToken<List<CartItem>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toCartItemJson(list: List<CartItem>): String {
        return Gson().toJson(list)
    }
}