package com.sign.led.ui.signFullView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sign.led.databinding.ActivitySignFullViewBinding

class signFullViewActivity : AppCompatActivity() {


    private lateinit var binding:ActivitySignFullViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFullViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {

    }
}