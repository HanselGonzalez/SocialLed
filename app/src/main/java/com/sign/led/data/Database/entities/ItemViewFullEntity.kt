package com.sign.led.data.Database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sign.led.domain.model.ItemViewFullModel
import com.sign.led.domain.model.TextModel


@Entity(tableName = "signs_table")
data class ItemViewFullEntity (

    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    @ColumnInfo("name") val name:String,
    @ColumnInfo("background_color") val backgroundColor:Int?,
    @ColumnInfo("background_image") val backgroundImage:Boolean,
    @ColumnInfo("texts") val textListFinal:String

){
    fun toDomain():ItemViewFullModel{
        val gson = Gson()
        val listType = object : TypeToken<List<TextModel>>() {}.type
        val textList : List<TextModel> = gson.fromJson(textListFinal,listType)

        return ItemViewFullModel(
            id,
            name,
            backgroundColor,
            backgroundImage,
            textList
        )
    }
}