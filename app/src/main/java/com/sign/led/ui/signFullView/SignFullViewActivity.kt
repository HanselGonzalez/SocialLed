package com.sign.led.ui.signFullView

import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.sign.led.R
import com.sign.led.databinding.ActivitySignFullViewBinding
import com.sign.led.ui.Singleton.ListItemsFullViewSingleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignFullViewActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignFullViewBinding
    private val signFullViewModel: SignFullViewModel by viewModels()
    private lateinit var flBackground: FrameLayout
    private val args:SignFullViewActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignFullViewBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            controller?.let {
                it.hide(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE)
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {

            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setContentView(binding.root)




        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        initUI()
    }


    private fun initUI() {
        initState()
    }

    private fun initState() {
        flBackground = binding.flBackground

        flBackground.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                flBackground.viewTreeObserver.removeOnGlobalLayoutListener(this)

                when(args.type){
                    "signTemporal" -> initUILocal()
                    "signBd" -> initUIState()
                }

            }
        })



        initListeners()
    }


    private fun initListeners() {
        initFlToolBar()

        flBackground.setOnClickListener {
            openToolbarGoBack()
        }

        binding.btnBackFullView.setOnClickListener {
            flBackground.removeAllViews()
            onBackPressed()
        }

        binding.tvGoBack.setOnClickListener {
            flBackground.removeAllViews()
            onBackPressed()
        }



    }

    private fun initFlToolBar() {
        binding.flToolBar.animate()
            .translationY(-binding.flToolBar.height.toFloat())
            .setDuration(140)
            .withEndAction {
                binding.flToolBar.isVisible = false
            }
            .start()
    }

    private fun openToolbarGoBack() {
        if (binding.flToolBar.isVisible) {
            binding.flToolBar.animate()
                .translationY(-binding.flToolBar.height.toFloat())
                .setDuration(140)
                .withEndAction {
                    binding.flToolBar.isVisible = false
                }
                .start()
        } else {
            binding.flToolBar.translationY = -binding.flToolBar.height.toFloat()
            binding.flToolBar.animate()
                .translationY(0f)
                .setDuration(140)
                .withStartAction {
                    binding.flToolBar.isVisible = true
                }
                .start()
        }
    }


    override fun onBackPressed() {
        flBackground.removeAllViews()
        super.onBackPressed()

    }






private fun initUILocal() {
    val listItemsFinal = ListItemsFullViewSingleton.getListItems()

    if (listItemsFinal.backgroundColor != null) {
        flBackground.setBackgroundColor(listItemsFinal.backgroundColor)
    } else {
        flBackground.setBackgroundColor(getColor(R.color.black))
    }


    lateinit var backgroundFinal: ImageView

    if (listItemsFinal.backgroundImage) {
        backgroundFinal = ImageView(this)
        backgroundFinal.setImageResource(R.drawable.background_pixel)
        backgroundFinal.scaleType = ImageView.ScaleType.FIT_XY

        val imageLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT

        )
        backgroundFinal.layoutParams = imageLayoutParams

        flBackground.addView(backgroundFinal)
    }

    listItemsFinal.listText?.forEach { textItem ->

        val newText = TextView(this)
        newText.text = textItem.text
        newText.textSize = textItem.size
        newText.maxLines = 1
        newText.setTextColor(textItem.color)
        newText.typeface = Typeface.createFromAsset(this.assets, textItem.typeface)

        val relativeX = textItem.positionX
        val relativeY = textItem.positionY

        val absoluteX = relativeX * flBackground.width
        val absoluteY = relativeY * flBackground.height


        val animationNewText =
            AnimationUtils.loadAnimation(this, textItem.animation)
        animationNewText.duration = textItem.speedAnimation

        newText.animation = animationNewText
        newText.startAnimation(newText.animation)

        newText.setPadding(20, 10, 20, 20)


        val textLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT

        )

        newText.layoutParams = textLayoutParams


        newText.x = absoluteX
        newText.y = absoluteY

        flBackground.addView(newText, 0)
    }
}


private fun initUIState() {
    signFullViewModel.getSignById(args.id)

    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            signFullViewModel.state.collect() {
                when (it) {
                    is SignFullState.Error -> errorState()
                    SignFullState.Loading -> loadingState()
                    is SignFullState.Success -> successState(it)
                }
            }
        }
    }
}

private fun successState(state: SignFullState.Success) {

    binding.pbViewFullSign.isVisible = false

    val listFinalBd = state.idItem

    if (listFinalBd.backgroundColor != null) {
        flBackground.setBackgroundColor(listFinalBd.backgroundColor)
    } else {
        flBackground.setBackgroundColor(getColor(R.color.black))
    }

    lateinit var backgroundFinal: ImageView

    if (listFinalBd.backgroundImage) {
        backgroundFinal = ImageView(this)
        backgroundFinal.setImageResource(R.drawable.background_pixel)
        backgroundFinal.scaleType = ImageView.ScaleType.FIT_XY

        val imageLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT

        )
        backgroundFinal.layoutParams = imageLayoutParams

        flBackground.addView(backgroundFinal)
    }

    listFinalBd.listText?.forEach { textItem ->

        val newText = TextView(this)
        newText.text = textItem.text
        newText.textSize = textItem.size
        newText.maxLines = 1
        newText.setTextColor(textItem.color)
        newText.typeface = Typeface.createFromAsset(this.assets, textItem.typeface)

        val relativeX = textItem.positionX
        val relativeY = textItem.positionY

        val absoluteX = relativeX * flBackground.width
        val absoluteY = relativeY * flBackground.height


        val animationNewText =
            AnimationUtils.loadAnimation(this, textItem.animation)
        animationNewText.duration = textItem.speedAnimation

        newText.animation = animationNewText
        newText.startAnimation(newText.animation)

        newText.setPadding(20, 10, 20, 20)


        val textLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT

        )

        newText.layoutParams = textLayoutParams


        newText.x = absoluteX
        newText.y = absoluteY

        flBackground.addView(newText,0)
    }

}

private fun loadingState() {
binding.pbViewFullSign.isVisible = true
}

private fun errorState() {
    binding.pbViewFullSign.isVisible = false
}


}