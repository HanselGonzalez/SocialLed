package com.sign.led.data.providers

import com.sign.led.domain.model.ItemViewFullModelInfo
import com.sign.led.domain.model.ItemViewFullModelInfo.SignFour
import com.sign.led.domain.model.ItemViewFullModelInfo.SignOne
import com.sign.led.domain.model.ItemViewFullModelInfo.SignThree
import com.sign.led.domain.model.ItemViewFullModelInfo.SignTwo
import javax.inject.Inject

class SignsProvider @Inject constructor() {

    fun getSignsProvider():List<ItemViewFullModelInfo>{
        return listOf(
            SignOne,
            SignTwo,
            SignThree,
            SignFour
        )
    }

    fun getSignProviderById(id:Long):ItemViewFullModelInfo{
        return when(id){
            1L -> SignOne
            2L -> SignTwo
            3L -> SignThree
            4L -> SignFour
            else -> null!!
        }
    }

}