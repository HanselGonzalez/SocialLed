package com.sign.led.data.Database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sign.led.data.Database.entities.ItemViewFullEntity


@Dao
interface ItemViewFullDao {

    @Query("select * from signs_table")
    suspend fun getAllSigns():List<ItemViewFullEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSign(itemViewFull:ItemViewFullEntity)

    @Delete
    suspend fun deleteSign(itemViewFull: ItemViewFullEntity)

}