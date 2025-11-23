package no.kristiania.androidexam.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import no.kristiania.androidexam.data.CartItem

@Dao
interface ProductCartDao {
    @Query("SELECT * FROM cartitem")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM cartitem WHERE id = :itemId")
    suspend fun getCartItemById(itemId: Int): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Delete
    suspend fun removeFromCart(cartItem: CartItem)

    @Query("UPDATE cartitem SET quantity = quantity + 1 WHERE id = :itemId")
    suspend fun increaseQuantity(itemId: Int)

    @Query("UPDATE cartitem SET quantity = quantity -1 WHERE id = :itemId AND quantity > 1")
    suspend fun decreaseQuantity(itemId: Int)

    @Query("DELETE FROM cartitem")
    suspend fun clearCart()
}