package com.sign.led.ui.Singlenton

import com.sign.led.domain.model.TextModel

object ListItemsFullViewSingleton {

    private var listItems: MutableList<TextModel> = mutableListOf()

    fun getListItems(): MutableList<TextModel> {
        return listItems
    }

    fun setListItems(items: MutableList<TextModel>) {
        listItems = items
    }

}