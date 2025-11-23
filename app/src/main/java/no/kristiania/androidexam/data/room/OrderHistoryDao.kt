package no.kristiania.androidexam.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import no.kristiania.androidexam.data.Order

@Dao
interface OrderHistoryDao {
    @Query("SELECT * FROM `Order` ORDER BY orderDate DESC")
    fun getOrders(): Flow<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToOrderHistory(order: Order)
}