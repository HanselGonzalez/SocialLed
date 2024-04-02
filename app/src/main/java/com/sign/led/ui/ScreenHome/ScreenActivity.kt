package com.sign.led.ui.ScreenHome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sign.led.R
import com.sign.led.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        screenSplash.setKeepOnScreenCondition{true}
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}