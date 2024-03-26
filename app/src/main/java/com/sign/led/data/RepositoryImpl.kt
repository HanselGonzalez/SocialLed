package com.sign.led.data

import android.util.Log
import com.google.gson.Gson
import com.sign.led.data.Database.dao.ItemViewFullDao
import com.sign.led.data.Database.entities.ItemViewFullEntity
import com.sign.led.domain.Repository
import com.sign.led.domain.model.ItemViewFullModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val itemViewFullDao: ItemViewFullDao):Repository {


    override suspend fun getSigns(): List<ItemViewFullModel> {

        return withContext(Dispatchers.IO){
            try {
                val response = itemViewFullDao.getAllSigns()
                return@withContext response.map { it.toDomain() }
            }catch (e:Exception){
                Log.i("errorImpl","Ha ocurrido un error: ${e.message}")
            }

            return@withContext emptyList()
        }

    }

    override suspend fun createSign(itemsFull:ItemViewFullModel){

        return withContext(Dispatchers.IO){
            try{
                val entity = ItemViewFullEntity(
                    name = itemsFull.name!!,
                    backgroundColor = itemsFull.backgroundColor,
                    backgroundImage = itemsFull.backgroundImage,
                    textListFinal = Gson().toJson(itemsFull.listText)
                )

                itemViewFullDao.insertSign(entity)
            }catch (e:Exception){
                Log.i("errorImpl","Ha ocurrido un error: ${e.message}")
            }
        }

    }



}