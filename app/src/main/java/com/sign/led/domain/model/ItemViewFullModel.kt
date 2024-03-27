package com.sign.led.domain.model

data class ItemViewFullModel(

    val id:Long?,
    val name:String?,
    val backgroundColor: Int?,
    var backgroundImage: Boolean,
    val listText: List<TextModel>

)