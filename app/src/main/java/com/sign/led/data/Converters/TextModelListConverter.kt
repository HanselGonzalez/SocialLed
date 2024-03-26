package com.sign.led.data.Converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sign.led.domain.model.TextModel

class TextModelListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value:String):List<TextModel>{
        val listType = object: TypeToken<List<TextModel>>(){}.type
        return gson.fromJson(value,listType)
    }

    @TypeConverter
    fun listToString(list:List<TextModel>):String{
        return gson.toJson(list)
    }

}