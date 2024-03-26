package com.sign.led.ui.mysigns.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sign.led.R
import com.sign.led.domain.model.ItemViewFullModel

class MySignsAdapter(private var itemsFull: List<ItemViewFullModel> = emptyList()):RecyclerView.Adapter<MySignsViewHolder>() {


    fun updateDate(itemsFull: List<ItemViewFullModel>){
        this.itemsFull = itemsFull
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySignsViewHolder {
        return MySignsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_signs, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MySignsViewHolder, position: Int) {
        holder.render(itemsFull[position])
    }

    override fun getItemCount(): Int = itemsFull.size


}