package com.sign.led.ui.signs.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sign.led.R
import com.sign.led.domain.model.ItemViewFullModelInfo

class SignsAdapter(private var signsList:List<ItemViewFullModelInfo> = emptyList(), private val navigateToFullView:(Long) -> Unit):RecyclerView.Adapter<SignsViewHolder>() {

    fun updateData(signsList: List<ItemViewFullModelInfo>){
        this.signsList = signsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignsViewHolder {
        return SignsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_signs_provider, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SignsViewHolder, position: Int) {

        holder.render(signsList[position], navigateToFullView )

    }

    override fun getItemCount(): Int = signsList.size
}