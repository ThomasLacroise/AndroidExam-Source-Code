package no.kristiania.androidexam.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.kristiania.androidexam.data.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM Product WHERE :productId = id")
    suspend fun getProductById(productId: Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(product: List<Product>)

}