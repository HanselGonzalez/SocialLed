package com.sign.led.data.providers

import android.content.Context
import com.sign.led.domain.model.ItemViewFullModelInfo
import com.sign.led.domain.model.ItemViewFullModelInfo.SignFour
import com.sign.led.domain.model.ItemViewFullModelInfo.SignOne
import com.sign.led.domain.model.ItemViewFullModelInfo.SignThree
import com.sign.led.domain.model.ItemViewFullModelInfo.SignTwo
import javax.inject.Inject

class SignsProvider @Inject constructor(private val context:Context) {

    fun getSignsProvider():List<ItemViewFullModelInfo>{
        return listOf(
            SignOne(context),
            SignTwo(context),
            SignThree(context),
            SignFour(context)
        )
    }

    fun getSignProviderById(id:Long):ItemViewFullModelInfo{
        return when(id){
            1L -> SignOne(context)
            2L -> SignTwo(context)
            3L -> SignThree(context)
            4L -> SignFour(context)
            else -> null!!
        }
    }

}