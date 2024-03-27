package com.sign.led.domain

import com.sign.led.domain.model.ItemViewFullModel

interface Repository {

    suspend fun getSigns():List<ItemViewFullModel>
    suspend fun createSign(itemsFull:ItemViewFullModel)
    suspend fun deleteSign(itemsFull: ItemViewFullModel)
    suspend fun getSignById(idItem: Long):ItemViewFullModel?


}