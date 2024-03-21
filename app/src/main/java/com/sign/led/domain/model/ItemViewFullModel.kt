package com.sign.led.domain.model

data class ItemViewFullModel(

    val id: String,
    val backgroundColor: Int,
    val backgroundImage: Boolean,
    val listText: List<TextModel>

)