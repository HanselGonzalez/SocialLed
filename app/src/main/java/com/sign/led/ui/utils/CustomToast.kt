package com.sign.led.ui.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.sign.led.R

object CustomToast {

    fun showCustomToast(context: Context, message: String) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.toast_custom, null)

        val textViewMessage = layout.findViewById<TextView>(R.id.textViewMessage)
        textViewMessage.text = message

        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 230)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

}