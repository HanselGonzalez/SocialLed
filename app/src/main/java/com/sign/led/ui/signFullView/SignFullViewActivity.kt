package com.sign.led.ui.signFullView

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sign.led.databinding.ActivitySignFullViewBinding
import com.sign.led.ui.Singlenton.ListItemsFullViewSingleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignFullViewActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignFullViewBinding
    private val signFullViewModel: SignFullViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFullViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        initUI()
    }

    private fun initUI() {
        initState()
    }

    private fun initState() {

        initUILocal()
        initUIState()
    }


    private fun initUILocal(){
        val background = binding.flBackground
        val listItemsFinal = ListItemsFullViewSingleton.getListItems()



        listItemsFinal?.forEach { textItem ->
            val newText = TextView(this)

            newText.text = textItem.text

            val textLayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT

            )

            newText.layoutParams = textLayoutParams

            background.addView(newText)
        }
    }


    private fun initUIState() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                signFullViewModel.state.collect(){
                    when(it){
                        is SignFullState.Error -> errorState()
                        SignFullState.Loading -> loadingState()
                        is SignFullState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun successState(state:SignFullState.Success) {

    }

    private fun loadingState() {

    }

    private fun errorState() {

    }
}