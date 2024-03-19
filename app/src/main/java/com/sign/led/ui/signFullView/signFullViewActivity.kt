package com.sign.led.ui.signFullView

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sign.led.databinding.ActivitySignFullViewBinding

class signFullViewActivity : AppCompatActivity() {


    private lateinit var binding:ActivitySignFullViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFullViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        initUI()
    }

    private fun initUI() {

    }
}