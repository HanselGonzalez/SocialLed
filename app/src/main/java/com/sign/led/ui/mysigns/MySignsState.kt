package com.sign.led.ui.mysigns

import com.sign.led.domain.model.ItemViewFullModel

sealed class MySignsState {

    data object Initial:MySignsState()
    data object Loading:MySignsState()
    data class Error(val error:String):MySignsState()
    data class Success(val itemsFull:List<ItemViewFullModel>):MySignsState()

}