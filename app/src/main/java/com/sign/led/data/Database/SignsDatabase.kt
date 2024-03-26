package com.sign.led.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sign.led.data.Database.dao.ItemViewFullDao
import com.sign.led.data.Database.entities.ItemViewFullEntity

@Database(entities = [ItemViewFullEntity::class], version = 1)
abstract class SignsDatabase:RoomDatabase() {

    abstract fun getItemViewFullDao():ItemViewFullDao


}