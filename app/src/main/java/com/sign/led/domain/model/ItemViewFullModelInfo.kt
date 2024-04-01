package com.sign.led.domain.model

import android.content.Context
import com.sign.led.R

sealed class ItemViewFullModelInfo (val id:Long, val name:Int, val backgroundColor:Int, val backgroundImage:Boolean, val listText:List<TextModel>){

    data class SignOne(private val context: Context) : ItemViewFullModelInfo(
        1, R.string.welcome, -2354116, false, listText = listOf(
            TextModel(
                1,
                context.getString(R.string.welcome_caps),
                110.25f,
                "fugazone.ttf",
                -1,
                R.anim.anim_float_text,
                2000,
                0.12446536f,
                0.28564453f
            ),
            TextModel(
                2,
                context.getString(R.string.welcome_caps),
                110.25f,
                "fugazone.ttf",
                -16316375,
                R.anim.anim_float_text,
                2000,
                0.12446536f,
                -0.00953311f
            ),
            TextModel(
                3,
                context.getString(R.string.welcome_caps),
                110.25f,
                "fugazone.ttf",
                -16316375,
                R.anim.anim_float_text,
                2000,
                0.12446536f,
                0.5730329f
            )

        )
    )

    data class SignTwo(private val context: Context) : ItemViewFullModelInfo(
        2, R.string.study_time, -11788936, false, listText = listOf(
            TextModel(
                1,
                context.getString(R.string.study_time),
                131.25f,
                "dmseriftext.ttf",
                -1,
                R.anim.anim_blink,
                700,
                0.113896236f,
                0.3460984f
            ),
            TextModel(
                2,
                context.getString(R.string.study_time),
                99.60388f,
                "dmseriftext.ttf",
                -16316375,
                R.anim.anim_blink,
                700,
                0.19565052f,
                0.03348214f
            )
        )
    )

    data class SignThree(private val context: Context) : ItemViewFullModelInfo(
        3, R.string.motivation, -16777216, false, listText = listOf(
            TextModel(
                1,
                context.getString(R.string.welcome_phrase_motivation_1),
                81.33911f,
                "leaguegothic.ttf",
                -2448096,
                R.anim.anim_none,
                0,
                0.13715526f,
                0.26832217f
            ),
            TextModel(
                2,
                context.getString(R.string.welcome_phrase_motivation_2),
                52.5f,
                "dmsan.ttf",
                -2448096,
                R.anim.anim_none,
                0,
                0.32597408f,
                0.6603423f
            )
        )
    )

    data class SignFour(private val context: Context) : ItemViewFullModelInfo(
        4, R.string.live_streaming, -13151254, false, listText = listOf(
            TextModel(
                1,
                context.getString(R.string.live_streaming),
                131.25f,
                "leaguegothic.ttf",
                -1,
                R.anim.anim_blink,
                1000,
                0.15818922f,
                0.26506695f
            ),
            TextModel(
                2,
                context.getString(R.string.live_streaming2),
                131.25f,
                "leaguegothic.ttf",
                -65536,
                R.anim.anim_blink,
                1000,
                0.7858006f,
                0.27297246f
            )
        )
    )

}