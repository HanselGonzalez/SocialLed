package com.sign.led.domain.model

import com.sign.led.R

sealed class ItemViewFullModelInfo (val id:Long, val name:Int, val backgroundColor:Int, val backgroundImage:Boolean, val listText:List<TextModel>){

    data object SignOne:ItemViewFullModelInfo(1,R.string.welcome, -2354116,false, listText = listOf(
        TextModel(1,"WELCOME",131.25f, "fugazone.ttf", -1, R.anim.anim_float_text, 2000, 0.12446536f,0.28564453f),
        TextModel(2,"WELCOME",131.25f, "fugazone.ttf", -16316375, R.anim.anim_float_text, 2000, 0.12446536f,-0.00953311f),
        TextModel(3,"WELCOME",131.25f, "fugazone.ttf", -16316375, R.anim.anim_float_text, 2000, 0.12446536f,0.5730329f)

    ))

    data object SignTwo:ItemViewFullModelInfo(2,R.string.study_time, -11788936,false, listText = listOf(
        TextModel(1,"Study time",131.25f, "dmseriftext.ttf", -1, R.anim.anim_blink, 700, 0.113896236f,0.3460984f),
        TextModel(2,"Study time",99.60388f, "dmseriftext.ttf", -16316375, R.anim.anim_blink, 700, 0.19565052f,0.03348214f)
        ))

    data object SignThree:ItemViewFullModelInfo(3,R.string.motivation, -16777216,false, listText = listOf(
        TextModel(1,"You are destined for something",81.33911f, "leaguegothic.ttf", -2448096, R.anim.anim_none, 0, 0.13715526f,0.26832217f),
        TextModel(2,"try and find it",52.5f, "dmsan.ttf", -2448096, R.anim.anim_none, 0, 0.32597408f,0.6603423f)))

    data object SignFour:ItemViewFullModelInfo(4,R.string.live_streaming, -13151254,false, listText = listOf(
        TextModel(1,"Live Streaming",131.25f, "leaguegothic.ttf", -1, R.anim.anim_blink, 1000, 0.15818922f,0.26506695f),
        TextModel(2,"0",131.25f, "leaguegothic.ttf", -65536, R.anim.anim_blink, 1000, 0.7858006f,0.27297246f)
        ))

}