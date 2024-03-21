package com.sign.led.ui.signFullView

import com.sign.led.domain.model.TextModel

sealed class SignFullState {

    data object Loading : SignFullState()
    data class Error(val error: String) : SignFullState()
    data class Success(val listItems: List<TextModel>?) : SignFullState()

}