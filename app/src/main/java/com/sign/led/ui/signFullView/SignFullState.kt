package com.sign.led.ui.signFullView

import com.sign.led.domain.model.ItemViewFullModel
import com.sign.led.domain.model.ItemViewFullModelInfo

sealed class SignFullState {

    data object Loading : SignFullState()
    data class Error(val error: String) : SignFullState()
    data class Success(val idItem:ItemViewFullModel) : SignFullState()
    data class SuccessProvider(val idItem: ItemViewFullModelInfo) : SignFullState()

}