package com.sign.led.ui.mysigns.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sign.led.databinding.ItemSignsBinding
import com.sign.led.domain.model.ItemViewFullModel

class MySignsViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val binding = ItemSignsBinding.bind(view)

    fun render(itemsFull:ItemViewFullModel){

        binding.tvNameSign.text = itemsFull.name

    }
}