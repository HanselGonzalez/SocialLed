package com.sign.led.ui.signFullView

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowInsets
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
    private val args: SignFullViewActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignFullViewBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.setOnApplyWindowInsetsListener { _, insets ->
                insets
            }
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(binding.root)




        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        initUI()
    }


    private fun initUI() {
        flBackground = binding.flBackground
        initState()
    }

    private fun initState() {


        flBackground.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                flBackground.viewTreeObserver.removeOnGlobalLayoutListener(this)

                when (args.type) {
                    "signTemporal" -> initUILocal()
                    "signBd" -> {
                        signFullViewModel.getSignById(args.id)
                        initUIState()
                    }
                    "signProvider" -> {
                        signFullViewModel.getSignProviderById(args.id)
                        initUIState()
                    }
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
            onBackPressed()
        }

        binding.tvGoBack.setOnClickListener {
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


    private fun initUILocal() {
        val animatorSet = AnimatorSet()

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

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenHeight = displayMetrics.heightPixels
            val densityScreen = displayMetrics.density

            val sizeRest = (screenHeight / 100) + densityScreen

            newText.textSize = textItem.size - sizeRest


            newText.maxLines = 1
            newText.setTextColor(textItem.color)
            newText.typeface = Typeface.createFromAsset(this.assets, textItem.typeface)

            val textWidth = newText.paint.measureText(newText.text.toString())



            val textLayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT

            )

            val relativeX = textItem.positionX
            val relativeY = textItem.positionY

            val absoluteX = relativeX * flBackground.width
            val absoluteY = relativeY * flBackground.height



            val animationResourceTypeName =
                this.resources.getResourceTypeName(textItem.animation)

            if (animationResourceTypeName == "animator") {

                val animationFinalPreviewAnimator  =
                    AnimatorInflater.loadAnimator(this, textItem.animation)
                animationFinalPreviewAnimator.duration = textItem.speedAnimation
                animationFinalPreviewAnimator.setTarget(newText)
                animatorSet.play(animationFinalPreviewAnimator)



            } else if (animationResourceTypeName == "anim") {

                if(textItem.animation == R.anim.anim_horizontal_displacement && textWidth > flBackground.width.toFloat()){

                    newText.layoutParams.width = textWidth.toInt()
                    newText.ellipsize = TextUtils.TruncateAt.MARQUEE
                    newText.isSingleLine = true
                    newText.isSelected = true


                }else{
                    val animation =
                        AnimationUtils.loadAnimation(this, textItem.animation)
                    animation.duration = textItem.speedAnimation
                    newText.animation = animation
                    newText.startAnimation(animation)
                }
            }

            newText.setPadding(20, 20, 20, 20)

            newText.layoutParams = textLayoutParams

            newText.x = absoluteX
            newText.y = absoluteY








            flBackground.addView(newText, 0)
        }

        animatorSet.start()
    }


    private fun initUIState() {


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signFullViewModel.state.collect() {
                    when (it) {
                        is SignFullState.Error -> errorState()
                        SignFullState.Loading -> loadingState()
                        is SignFullState.Success -> successState(it)
                        is SignFullState.SuccessProvider -> successProvider(it)
                        else -> {}
                    }
                }
            }
        }
    }


    //Provider
    private fun successProvider(state: SignFullState.SuccessProvider) {
        val animatorSet = AnimatorSet()
        binding.pbViewFullSign.isVisible = false

        val listFinalProvider = state.idItem

        flBackground.setBackgroundColor(listFinalProvider.backgroundColor)


        lateinit var backgroundFinal: ImageView

        if (listFinalProvider.backgroundImage) {
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

        listFinalProvider.listText?.forEach { textItem ->

            val newText = TextView(this)
            newText.text = textItem.text
            newText.textSize = textItem.size

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenHeight = displayMetrics.heightPixels
            val densityScreen = displayMetrics.density

            val sizeRest = (screenHeight / 100) + densityScreen

            newText.textSize = textItem.size - sizeRest

            newText.maxLines = 1
            newText.setTextColor(textItem.color)
            newText.typeface = Typeface.createFromAsset(this.assets, textItem.typeface)


            val textWidth = newText.paint.measureText(newText.text.toString())


            val relativeX = textItem.positionX
            val relativeY = textItem.positionY

            val absoluteX = relativeX * flBackground.width
            val absoluteY = relativeY * flBackground.height


            val animationResourceTypeName =
                this.resources.getResourceTypeName(textItem.animation)

            if (animationResourceTypeName == "animator") {

                val animationFinalPreviewAnimator  =
                    AnimatorInflater.loadAnimator(this, textItem.animation)
                animationFinalPreviewAnimator.duration = textItem.speedAnimation
                animationFinalPreviewAnimator.setTarget(newText)
                animatorSet.play(animationFinalPreviewAnimator)



            } else if (animationResourceTypeName == "anim") {

                if(textItem.animation == R.anim.anim_horizontal_displacement && textWidth > flBackground.width.toFloat()){

                    newText.layoutParams.width = textWidth.toInt()
                    newText.ellipsize = TextUtils.TruncateAt.MARQUEE
                    newText.isSingleLine = true
                    newText.isSelected = true


                }else{
                    val animation =
                        AnimationUtils.loadAnimation(this, textItem.animation)
                    animation.duration = textItem.speedAnimation
                    newText.animation = animation
                    newText.startAnimation(animation)
                }
            }

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

        animatorSet.start()

    }




    //BD
    private fun successState(state: SignFullState.Success) {
        val animatorSet = AnimatorSet()

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

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenHeight = displayMetrics.heightPixels
            val densityScreen = displayMetrics.density

            val sizeRest = (screenHeight / 100) + densityScreen

            newText.textSize = textItem.size - sizeRest

            newText.maxLines = 1
            newText.setTextColor(textItem.color)
            newText.typeface = Typeface.createFromAsset(this.assets, textItem.typeface)


            val textWidth = newText.paint.measureText(newText.text.toString())


            val relativeX = textItem.positionX
            val relativeY = textItem.positionY

            val absoluteX = relativeX * flBackground.width
            val absoluteY = relativeY * flBackground.height


            val textLayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT

            )

            newText.layoutParams = textLayoutParams

            val animationResourceTypeName =
                this.resources.getResourceTypeName(textItem.animation)

            if (animationResourceTypeName == "animator") {

                val animationFinalPreviewAnimator  =
                    AnimatorInflater.loadAnimator(this, textItem.animation)
                animationFinalPreviewAnimator.duration = textItem.speedAnimation
                animationFinalPreviewAnimator.setTarget(newText)
                animatorSet.play(animationFinalPreviewAnimator)



            } else if (animationResourceTypeName == "anim") {

                if(textItem.animation == R.anim.anim_horizontal_displacement && textWidth > flBackground.width.toFloat()){

                    newText.layoutParams.width = textWidth.toInt()
                    newText.ellipsize = TextUtils.TruncateAt.MARQUEE
                    newText.isSingleLine = true
                    newText.isSelected = true


                }else{
                    val animation =
                        AnimationUtils.loadAnimation(this, textItem.animation)
                    animation.duration = textItem.speedAnimation
                    newText.animation = animation
                    newText.startAnimation(animation)
                }
            }

            newText.setPadding(20, 20, 20, 20)





            newText.x = absoluteX
            newText.y = absoluteY

            flBackground.addView(newText, 0)
        }

        animatorSet.start()


    }

    private fun loadingState() {
        binding.pbViewFullSign.isVisible = true
    }

    private fun errorState() {
        binding.pbViewFullSign.isVisible = false
    }


    override fun onBackPressed() {
        super.onBackPressed()
        flBackground.removeAllViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        flBackground.removeAllViews()
    }


}