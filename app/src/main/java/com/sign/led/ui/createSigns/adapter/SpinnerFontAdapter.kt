package com.sign.led.ui.createSigns.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.sign.led.R
import com.sign.led.domain.model.SpinnerFontModel

class SpinnerFontAdapter(context: Context, options:List<SpinnerFontModel>):ArrayAdapter<SpinnerFontModel>(context, R.layout.spinner_selected, options) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_selected, parent, false)
        val itemSelected = getItem(position)

        itemSelected?.let {
            val tvSpinnerFont = view.findViewById<TextView>(R.id.tvSpinner)
            tvSpinnerFont.text = it.text
            tvSpinnerFont.typeface = it.font


        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: inflater.inflate(R.layout.spinner_dropdown_items, parent, false)
        val itemSelected = getItem(position)

        itemSelected?.let {
            val tvSpinnerFont = view.findViewById<TextView>(R.id.tvSpinner)
            tvSpinnerFont.text = it.text
            tvSpinnerFont.typeface = it.font

        }

        return view
    }

    fun getSelectedFontTypeface(position: Int): Typeface? {
        val itemSelected = getItem(position)
        return itemSelected?.font
    }


}