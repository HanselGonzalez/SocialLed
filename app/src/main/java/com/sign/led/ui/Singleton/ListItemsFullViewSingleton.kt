package com.sign.led.ui.Singleton

import android.util.Log
import com.sign.led.domain.model.ItemViewFullModel

object ListItemsFullViewSingleton {

    private lateinit var listItems : ItemViewFullModel

    fun getListItems(): ItemViewFullModel {
        Log.i("listItemsAver", "$listItems")
        return listItems
    }

    fun setListItems(items: ItemViewFullModel) {
        Log.i("listItemsAverSiActualiza", "$items")

        listItems = items
    }

}