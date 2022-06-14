package kg.geektech.mouth8work1.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kg.geektech.mouth8work1.data.model.ShopItemDBModel

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_item")
    fun getAll(): LiveData<List<ShopItemDBModel>>//LiveData живая память

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDBModel: ShopItemDBModel)// suspend это для корутин

    @Delete
    suspend fun deleteShopItem(shopItemDBModel: ShopItemDBModel)

    @Update
    suspend fun editShopItem(shopItemDBModel: ShopItemDBModel)
}